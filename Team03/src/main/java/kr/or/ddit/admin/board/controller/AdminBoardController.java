package kr.or.ddit.admin.board.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.admin.board.exception.AdminBoardException;
import kr.or.ddit.admin.board.service.AdminBoardService;
import kr.or.ddit.board.service.BoardService;
import kr.or.ddit.comment.service.CommentService;
import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.paging.renderer.DefaultPaginationRenderer;
import kr.or.ddit.paging.renderer.PaginationRenderer;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.CommentVO;

@Controller
@RequestMapping("/admin/board")
public class AdminBoardController {
	
	public static final String MODELNAME = "board";
	
	@Autowired
	private AdminBoardService service;
	
	@Autowired
	private CommentService commentservice;
	
	@ModelAttribute(MODELNAME)
	public BoardVO board() {
		return new BoardVO();
	}
	
	
	
	// 시간 계산
	private String formatTimeDifference(LocalDateTime boardDateTime) {
		if (boardDateTime == null) {
	        return "작성일시 없음";
	    }
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
	
	// 관리자 커뮤니티 게시글 목록
	@GetMapping
	public String list(
			Optional<Integer> page ,
			@ModelAttribute("condition") SimpleCondition simpleCondition,
			Model model
	) {
		PaginationInfo paging = new PaginationInfo();
		paging.setCurrentPage(page.orElse(1));
		paging.setSimpleCondition(simpleCondition);
		
		List<BoardVO> adminBoardList = service.readAdminBoardList(paging);
		
		// 총 게시글 수 가져오기
	    int totalCount = service.getAdminTotalCount(paging);
		
		// 게시글 작성시간 차이 계산
	    for (BoardVO board : adminBoardList) {
	        LocalDateTime boardDateTime = board.getBoardDt();  // 작성일시 가져오기
	        String timeDifference = formatTimeDifference(boardDateTime);
	        board.setTimeDifference(timeDifference);  // BoardVO에 계산된 시간차 추가
	    }
		
		PaginationRenderer renderer = new DefaultPaginationRenderer();
		String pagingHTML = renderer.renderPagination(paging, null);
		
		model.addAttribute("adminBoardList", adminBoardList);
		model.addAttribute("pagingHTML", pagingHTML);
		model.addAttribute("totalCount", totalCount);
		
		return "admin/board/adminBoardList";
	}
	
	
	// 관리자 커뮤니티 게시글 상세보기
 	@GetMapping("{boardNo}")
	public String detail(@PathVariable int boardNo, Model model) {
		BoardVO board = service.readAdminBoard(boardNo);
		
		List<CommentVO> commentList = commentservice.readCommentList(boardNo);
		model.addAttribute("board",board);
		model.addAttribute("commentList",commentList);
		
		return "admin/board/adminBoardDetail";
	}
	
	
 	// 관리자 커뮤니티 게시글 등록 폼
	@GetMapping("/adminBoardForm")
	public String insertform() {
		return "admin/board/adminBoardForm";
	}
	
	// 관리자 커뮤니티 게시글 등록 처리
	@PostMapping("/adminBoardForm")
	public String insert(
		@ModelAttribute(MODELNAME) BoardVO board,
		BindingResult errors,
		RedirectAttributes redirectAttributes
	)throws IOException {
		
		redirectAttributes.addFlashAttribute(MODELNAME, board);
		String lvn = null;
		if(!errors.hasErrors()) {
			ServiceResult result = service.createAdminBoard(board);
			switch(result) {
			case OK: 
				lvn = "redirect:/admin/board";
				break;
			default:
				lvn ="board/adminBoardForm";
				redirectAttributes.addFlashAttribute("message","서버오류, 잠시 뒤 다시시도.");
				break;
			}
		}else {
			String errAttrName = BindingResult.MODEL_KEY_PREFIX + MODELNAME;
			redirectAttributes.addFlashAttribute(errAttrName,errors);
			lvn = "redirect:/admin/board";
		}
		return lvn;
	}
	
	
	// 관리자 커뮤니티 수정폼
	@GetMapping("{boardNo}/adminBoardEdit")
	public String updateForm(
			@PathVariable("boardNo") int boardNo
			, Model model
	) {
			BoardVO board = service.readAdminBoard(boardNo);
			model.addAttribute(MODELNAME,board);
			
		return "admin/board/adminBoardEdit";
	}
	
	// 관리자 커뮤니티 수정처리
	@PostMapping("{boardNo/boardEdit")
	public String update(
			@ModelAttribute(MODELNAME) BoardVO board
			, BindingResult errors
			, RedirectAttributes redirectAttributes
	) {
		String lvn = null;
		if(!errors.hasErrors()) {
			board.setFile(null);
			ServiceResult result = service.modifyAdminBoard(board);
			switch (result) {
			case OK:
				lvn = "redirect:/admin/board/"+board.getBoardNo();
				break;
				
			default:
				lvn = "redirect:/admin/board";
				redirectAttributes.addFlashAttribute("message","서버오류");
				break;
			}
		}else {
			String errAttrName = BindingResult.MODEL_KEY_PREFIX + MODELNAME;
	         redirectAttributes.addFlashAttribute(errAttrName, errors);
	         lvn = "redirect:/admin/board";
		}
		return lvn;
	}
	
	// 관리자 커뮤니티 게시글 삭제처리(상태만Y/N 바꿈)
	@PostMapping("{boardNo}")
	public String delete(
		@PathVariable int boardNo
		, RedirectAttributes redirectAttributes
	) {
		String lvn = null;
		ServiceResult result = service.removeAdminBoard(boardNo);
		switch (result) {
			case OK:
				lvn = "redirect:/admin/board";
				break;
				
			default:
				redirectAttributes.addFlashAttribute("message", "삭제 실패 !");
				lvn = "redirect:/admin/board";
				break;
		}
		return lvn;
	}
	
	@PostMapping("deleteMultiple")
	public String deleteMultiple(
		@RequestParam("boardNo") List<Integer> boardNo
		, RedirectAttributes redirectAttributes
	) {
		if (boardNo == null || boardNo.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "삭제할 게시글을 선택하세요.");
            return "redirect:/admin/board";
        }
        ServiceResult result = service.removeAdminBoards(boardNo);
        if (result == ServiceResult.OK) {
            redirectAttributes.addFlashAttribute("message", "선택한 게시글이 삭제되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("message", "게시글 삭제 실패.");
        }
        return "redirect:/admin/board";
	}
	
//-------------------------------------------------------------------------------------------------
		// 댓글 등록
		@PostMapping("{boardNo}/comment")
		public String insertComment(
			@PathVariable int boardNo,
	        @ModelAttribute CommentVO comment,
	        RedirectAttributes redirectAttributes,
	        Model model
		) {
			comment.setBoardNo(boardNo); 
			comment.setCommentDate(LocalDateTime.now());
			
	        ServiceResult result = commentservice.createComment(comment);
	        
	        if (result == ServiceResult.OK) {
	            redirectAttributes.addFlashAttribute("message", "댓글이 등록되었습니다.");
	        } else {
	            redirectAttributes.addFlashAttribute("message", "댓글 등록 실패.");
	        }
	        
	        // 게시물 상세 페이지로 리다이렉트하기 전에 댓글 목록을 다시 불러오기
	        List<CommentVO> commentList = commentservice.readCommentList(boardNo);

	        // 댓글 목록을 모델에 추가
	        model.addAttribute("commentList", commentList);

			return "redirect:/admin/board/{boardNo}";
		}
		
		 // 댓글 삭제
		 @PostMapping("{boardNo}/comment/delete")
		    public String deleteComment(@PathVariable("boardNo") int boardNo, 
		                                @RequestParam("commentDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime commentDate, 
		                                RedirectAttributes redirectAttributes) {

		    ServiceResult result = commentservice.deleteComment(commentDate);
		    
		    if (result == ServiceResult.OK) {
	            redirectAttributes.addFlashAttribute("message", "댓글이 삭제되었습니다.");
	        } else {
	            redirectAttributes.addFlashAttribute("message", "댓글 삭제에 실패했습니다.");
	        }
		    
		    return "redirect:/admin/board/" + boardNo;
		}
	
 //-------------------------------------------------------------------------------------------------
	
}
