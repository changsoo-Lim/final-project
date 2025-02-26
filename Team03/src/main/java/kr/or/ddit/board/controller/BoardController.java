package kr.or.ddit.board.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.board.service.BoardService;
import kr.or.ddit.comment.service.CommentService;
import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.file.service.FileService;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.paging.renderer.DefaultPaginationRenderer;
import kr.or.ddit.paging.renderer.PaginationRenderer;
import kr.or.ddit.vo.ApplyVO;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.CommentVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/main/board")
public class BoardController {
	public static final String MODELNAME = "board";
	
	@Inject
	private BoardService service;
	
	@Inject
	private CommentService commentservice;
	
	@Inject
	private FileService fileService;
	
	@ModelAttribute(MODELNAME)
	public BoardVO board() {
		return new BoardVO();
		
	}
	
	private String formatTimeDifference(LocalDateTime boardDateTime) {
		LocalDateTime now = LocalDateTime.now();
		long minutesAgo = ChronoUnit.MINUTES.between(boardDateTime, now);
	    long hoursAgo = ChronoUnit.HOURS.between(boardDateTime, now);
	    
	    if (minutesAgo < 60) {
	        return minutesAgo + "분 전";
	    } else if (hoursAgo < 24) {
	        return hoursAgo + "시간 전";
	    } else {
	        // 일주일 이상 지난 경우 날짜로 표시
	        return boardDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	    }
	    
	}
	
	
	// 커뮤니티 게시글 목록 조회
	@GetMapping
	public String list(
			Optional<Integer> page ,
			@ModelAttribute("condition") SimpleCondition simpleCondition,
			Model model
			
	) {
		PaginationInfo paging = new PaginationInfo();
		paging.setCurrentPage(page.orElse(1));
		paging.setSimpleCondition(simpleCondition);
		
		// 12개 목록으로 출력
		String targetJSP = "main/board/boardList";
	    if ("main/board/boardList".equals(targetJSP)) { 
	        paging.setScreenSize(15); 
	    } else {
	        paging.setScreenSize(10); 
	    }
		
		List<BoardVO> boardList = service.readBoardList(paging);
		
		// 총 게시글 수 가져오기
	    int totalCount = service.getTotalCount(paging);
		
		// 게시글 작성시간 차이 계산
	    for (BoardVO board : boardList) {
	        LocalDateTime boardDateTime = board.getBoardDt();  // 작성일시 가져오기
	        String timeDifference = formatTimeDifference(boardDateTime);
	        board.setTimeDifference(timeDifference);  // BoardVO에 계산된 시간차 추가
	    }
		
		
		PaginationRenderer renderer = new DefaultPaginationRenderer();
		String pagingHTML = renderer.renderPagination(paging, null);
		
		model.addAttribute("boardList", boardList);
		model.addAttribute("pagingHTML", pagingHTML);
		model.addAttribute("totalCount", totalCount); 
		
		return "main/board/boardList";
	}
	
	// 커뮤니티 게시글 목록 상세조회
	@GetMapping("{boardNo}")
	public String detail(@PathVariable int boardNo,Model model,Authentication authentication) {
		BoardVO board = service.readBoard(boardNo);
	// 댓글 목록부분
		List<CommentVO> commentList = commentservice.readCommentList(boardNo);
		
		// 날짜 포맷팅 로직
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	    for (CommentVO comment : commentList) {
	        if (comment.getCommentDate() != null) {
	        	comment.setFormattedCommentDate(comment.getCommentDate().format(formatter)); // 포맷된 날짜 설정
	        }
	    }
		
		// 현재 사용자 ID를 확인하고, 비로그인 사용자인 경우 null 처리
	    String currentUserId = null;
	    if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
	        currentUserId = ((UserDetails) authentication.getPrincipal()).getUsername();
	    }
	    model.addAttribute("currentUserId", currentUserId);
//		String currentUserId = ((UserDetails) authentication.getPrincipal()).getUsername();
//	    model.addAttribute("currentUserId", currentUserId);
		
		model.addAttribute("board",board);
		model.addAttribute("commentList",commentList);
		return "main/board/boardDetail";
	}
	
	// 커뮤니티 게시글 등록 폼
	@GetMapping("/boardForm")
	public String insertform() {
		return "main/board/boardForm";
	}
	
	// 커뮤니티 게시글 등록 처리
	@PostMapping("/boardForm")
	public String insert(
		@ModelAttribute(MODELNAME) BoardVO board
		,BindingResult errors
		, RedirectAttributes redirectAttributes
		, Authentication authentication
	)throws IOException {
		
		String memId = ((UserDetails) authentication.getPrincipal()).getUsername();
		board.setMemId(memId);
		
		
		redirectAttributes.addFlashAttribute(MODELNAME, board);
			String lvn = null;
			if(!errors.hasErrors()) {
				ServiceResult result = service.createBoard(board);
				switch(result) {
				case OK: 
					lvn = "redirect:/main/board";
					break;
				default:
					lvn ="board/boardForm";
					redirectAttributes.addFlashAttribute("message","서버오류, 잠시 뒤 다시시도.");
					break;
				}
			}else {
				String errAttrName = BindingResult.MODEL_KEY_PREFIX + MODELNAME;
				redirectAttributes.addFlashAttribute(errAttrName,errors);
				lvn = "redirect:/main/board";
			}
			return lvn;
		
	}
	
	// 커뮤니티 게시글 수정 폼
	@GetMapping("{boardNo}/boardEdit")
	public String updateForm(
			@PathVariable("boardNo") int boardNo
			, Model model
	) {
			BoardVO board = service.readBoard(boardNo);
			model.addAttribute(MODELNAME, board);
		
		return "main/board/boardEdit";
	}
	
	// 커뮤니티 게시글 수정처리
	@PostMapping("{boardNo}/boardEdit")
	public String update(
		@ModelAttribute(MODELNAME) BoardVO board
		, BindingResult errors
		, RedirectAttributes redirectAttributes
	) {
		String lvn = null;
		if(!errors.hasErrors()) {
			ServiceResult result = service.modifyBoard(board);
			switch (result) {
			case OK:
				lvn = "redirect:/main/board/"+board.getBoardNo();
				break;
				
			default:
				lvn = "redirect:/main/board";
				redirectAttributes.addFlashAttribute("message","서버오류");
				break;
			}
		}else {
			String errAttrName = BindingResult.MODEL_KEY_PREFIX + MODELNAME;
	         redirectAttributes.addFlashAttribute(errAttrName, errors);
	         lvn = "redirect:/main/board";
		}
		board.setFile(null);
		return lvn;
	}
	
	// 커뮤니티 게시글 삭제처리(상태만Y/N 바꿈)
	@PostMapping("{boardNo}")
	public String delete(
		@PathVariable int boardNo
		, RedirectAttributes redirectAttributes
	) {
		String lvn = null;
		ServiceResult result = service.removeBoard(boardNo);
		switch (result) {
			case OK:
				lvn = "redirect:/main/board";
				break;
				
			default:
				redirectAttributes.addFlashAttribute("message", "삭제 실패 !");
				lvn = "redirect:/main/board";
				break;
		}
		return lvn;
	}
	
	//-------------------------------------------------------------------------------------------------
	// 댓글 등록
	@PostMapping("{boardNo}/comment")
	public String insertComment(
		@PathVariable int boardNo,
        @ModelAttribute CommentVO comment,
        RedirectAttributes redirectAttributes,
        Authentication authentication,
        Model model
	) {
		
		
		String memId = ((UserDetails) authentication.getPrincipal()).getUsername();
		comment.setMemId(memId);
		comment.setBoardNo(boardNo); 
		comment.setCommentDate(LocalDateTime.now());
		
		
        ServiceResult result = commentservice.createComment(comment);
        if (result == ServiceResult.OK) {
  //          redirectAttributes.addFlashAttribute("message", "댓글이 등록되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "댓글 등록 실패.");
        }
        
        // 게시물 상세 페이지로 리다이렉트하기 전에 댓글 목록을 다시 불러오기
        List<CommentVO> commentList = commentservice.readCommentList(boardNo);

        // 댓글 목록을 모델에 추가
        model.addAttribute("commentList", commentList);

		return "redirect:/main/board/{boardNo}";
	}
	
	 // 댓글 삭제
	 @PostMapping("{boardNo}/comment/delete")
	    public String deleteComment(@PathVariable("boardNo") int boardNo, 
	                                @RequestParam("commentDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime commentDate, 
	                                RedirectAttributes redirectAttributes) {

	    ServiceResult result = commentservice.deleteComment(commentDate);
	    
	    if (result == ServiceResult.OK) {
//            redirectAttributes.addFlashAttribute("message", "댓글이 삭제되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "댓글 삭제에 실패했습니다.");
        }
	    
	    return "redirect:/main/board/" + boardNo;
	}
	 
	
	
	 
	 
}
