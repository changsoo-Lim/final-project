package kr.or.ddit.admin.review.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.admin.review.service.AdminReviewService;
import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.company.company.dao.CompanyMapper;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.paging.renderer.DefaultPaginationRenderer;
import kr.or.ddit.paging.renderer.PaginationRenderer;
import kr.or.ddit.vo.CompanyVO;
import kr.or.ddit.vo.ReviewVO;

@Controller
@RequestMapping("/admin/review")
public class AdminReviewController {
	
	@Inject
	private AdminReviewService service;  
	
	@Inject
	private CompanyMapper companyMapper;
	
	@GetMapping
	public String list(
			Optional<Integer> page ,
			@ModelAttribute("condition") SimpleCondition simpleCondition,
			Model model
	) {
		PaginationInfo paging = new PaginationInfo();
		paging.setCurrentPage(page.orElse(1));
		paging.setSimpleCondition(simpleCondition);
		
		// 여기만 12개 목록으로 출력
		String targetJSP = "member/review/myReviewList";
		if ("member/review/myReviewList".equals(targetJSP)) { 
			paging.setScreenSize(12); 
		} else {
			paging.setScreenSize(10); 
		}
		
		// 총 게시글 수 가져오기
	    int totalCount = service.getTotalCount(paging);
		
		List<ReviewVO> reviewList = service.readReviewList(paging);
		List<CompanyVO> companyList = companyMapper.selectCompanyList();
		
		PaginationRenderer renderer = new DefaultPaginationRenderer();
		String pagingHTML = renderer.renderPagination(paging, null);
		
		model.addAttribute("companyList", companyList);
		model.addAttribute("reviewList", reviewList);
		model.addAttribute("pagingHTML", pagingHTML);
		model.addAttribute("totalCount", totalCount); 
		
		return "admin/review/adminReviewList";
	}
	
	@PutMapping("{reviewNo}")
	public ResponseEntity<Map<String, Object>> statusUpdate(@PathVariable("reviewNo") int reviewNo) {
	    Map<String, Object> resp = new HashMap<>();
	    ServiceResult result = service.modifiyReviewStatus(reviewNo);

	    if (result == ServiceResult.OK) {
	        resp.put("success", true);
	        resp.put("message", "승인이 완료되었습니다.");
	    } else {
	        resp.put("success", false);
	        resp.put("message", "승인에 실패했습니다.");
	    }

	    return ResponseEntity.ok(resp); // JSON 응답 반환
	}

}
