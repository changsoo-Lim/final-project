package kr.or.ddit.report.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.inject.Inject;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.report.service.ReportService;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.ReportVO;

@Controller
@RequestMapping("/main/board/report")
public class ReportController {
	
	@Inject
	private ReportService service;
	
	@PostMapping("{boardNo}")
	public String report(
		@PathVariable("boardNo") int boardNo
		, ReportVO report
		, BoardVO board
		, RedirectAttributes redirectAttributes
		, Authentication authentication
	) {
//		String reportMemId = ((UserDetails) authentication.getPrincipal()).getUsername();
//	    report.setReportMemId(reportMemId);  // 신고자는 로그인한 사용자 아이디
//	    report.setReportedMemId(board.getMemId());  // 게시글 작성자의 아이디를 피신고자로 설정
		
		report.setBoardNo(boardNo);
		// 현재 날짜를 YYYYMMDD 형식으로 설정
	    String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
	    report.setReportDt(currentDate);
		
		ServiceResult result = service.reportBoard(report);
		if (result == ServiceResult.OK) {
            redirectAttributes.addFlashAttribute("message", "신고가 접수되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "신고 처리에 실패했습니다.");
        }
		
		return "redirect:/main/board/" + boardNo;
	}
	
	
}
