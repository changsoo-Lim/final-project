package kr.or.ddit.admin.report.controller;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.admin.report.service.AdminReportService;
import kr.or.ddit.blacklist.service.BlackListService;
import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.paging.renderer.DefaultPaginationRenderer;
import kr.or.ddit.paging.renderer.PaginationRenderer;
import kr.or.ddit.vo.BlacklistVO;
import kr.or.ddit.vo.ReportVO;

@Controller
@RequestMapping("admin/report")
public class AdminReportController {
	
	public static final String MODELNAME = "report";
	
	@Inject
	private AdminReportService service;
	
	@Inject
	private BlackListService blackListService;
	
	@ModelAttribute(MODELNAME)
	public ReportVO report() {
		return new ReportVO();
	}
	
	@GetMapping
	public String adminReportList(
			Optional<Integer> page ,
			@ModelAttribute("condition") SimpleCondition simpleCondition,
			Model model
			
	) {
		PaginationInfo paging = new PaginationInfo();
		paging.setCurrentPage(page.orElse(1));
		paging.setSimpleCondition(simpleCondition);
		
		List<ReportVO> adminReportList = service.readAdminReportList(paging);
		
		PaginationRenderer renderer = new DefaultPaginationRenderer();
		String pagingHTML = renderer.renderPagination(paging, null);
		
		model.addAttribute("adminReportList", adminReportList);
		model.addAttribute("pagingHTML", pagingHTML);
		
		return "admin/report/adminReportList";
	}
	
	@PostMapping("{reportNo}")
	public String inserBlacklist(
		  @PathVariable int reportNo
		, @ModelAttribute ReportVO report
		, BlacklistVO black
		, RedirectAttributes redirectAttributes
		, Model model
	) {
		
		report.setReportNo(reportNo);
		
		ServiceResult result = blackListService.createBlacklist(black);
		if (result == ServiceResult.OK) {
            redirectAttributes.addFlashAttribute("message", "블랙리스트에 추가되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "블랙리스트 처리에 실패했습니다.");
        }
		
		
		return "redirect:/admin/report";
	}
	
		
}
