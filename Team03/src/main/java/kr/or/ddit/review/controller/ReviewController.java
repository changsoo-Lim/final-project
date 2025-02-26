package kr.or.ddit.review.controller;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.company.company.dao.CompanyMapper;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.paging.renderer.DefaultPaginationRenderer;
import kr.or.ddit.paging.renderer.PaginationRenderer;
import kr.or.ddit.review.service.ReviewService;
import kr.or.ddit.vo.CompanyVO;
import kr.or.ddit.vo.EmployVO;
import kr.or.ddit.vo.ReviewVO;

@Controller
@RequestMapping("/review")
public class ReviewController {
	
	private final static String MODELNAME = "review";
	
	@ModelAttribute("review")
	public ReviewVO reviewModel() {
		return new ReviewVO();
	}

	@Inject
	private ReviewService service;
	
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
		String targetJSP = "main/review/reviewList";
	    if ("main/review/reviewList".equals(targetJSP)) { 
	        paging.setScreenSize(12); 
	    } else {
	        paging.setScreenSize(10); 
	    }
		
		// 총 게시글 수 가져오기
	    int totalCount = service.getTotalCount(paging);
		
		List<ReviewVO> reviewList = service.readMyReviewList(targetJSP, paging);
		List<CompanyVO> companyList = companyMapper.selectCompanyList();
		
		PaginationRenderer renderer = new DefaultPaginationRenderer();
		String pagingHTML = renderer.renderPagination(paging, null);
		
		model.addAttribute(MODELNAME, reviewList);
		model.addAttribute("companyList", companyList);
		model.addAttribute("pagingHTML", pagingHTML);
		model.addAttribute("totalCount", totalCount); 
		
		
		return "main/review/reviewList";
	}
	
	@GetMapping("myReview")
	public String myList(
			Authentication authentication,
			Optional<Integer> page ,
			@ModelAttribute("condition") SimpleCondition simpleCondition,
			Model model
			) {
		if(authentication==null) {
			return "redirect:/login";
		}
		String memId = ((UserDetails) authentication.getPrincipal()).getUsername();
		
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
		
		List<ReviewVO> reviewList = service.readMyReviewList(memId, paging);
		List<CompanyVO> companyList = companyMapper.selectCompanyList();
		
		PaginationRenderer renderer = new DefaultPaginationRenderer();
		String pagingHTML = renderer.renderPagination(paging, null);
		
		model.addAttribute(MODELNAME, reviewList);
		model.addAttribute("companyList", companyList);
		model.addAttribute("pagingHTML", pagingHTML);
		
		
		return "member/review/myReviewList";
	}
	
	@GetMapping("write")
	public String myReview(Authentication authentication,Model model) {
		if(authentication==null) {
			return "redirect:/login";
		}
		String memId = ((UserDetails) authentication.getPrincipal()).getUsername();
		
		List<EmployVO> myApplyList = service.readMyApplyList(memId);
		
		model.addAttribute("myApplyList", myApplyList);
		
		return "member/review/myReviewForm";
	}
	@PostMapping("write")
	public String myReviewFrom(
			Authentication authentication
			,ReviewVO reviewVO
			,BindingResult errors
			, RedirectAttributes redirectAttributes
	) {
		
		String memId = ((UserDetails) authentication.getPrincipal()).getUsername();
		
		reviewVO.setMemId(memId);
		
		ServiceResult result = service.createReview(reviewVO);
		
		redirectAttributes.addFlashAttribute(MODELNAME, reviewVO);
		
		String lvn ="";
		if(!errors.hasErrors()) {
			switch (result) {
			case OK:
				lvn = "redirect:/review/myReview";
				break;
	
			default:
				lvn ="member/review/myReviewForm";
				redirectAttributes.addFlashAttribute("message","서버오류, 잠시 뒤 다시시도.");
				break;
			}
		}else {
			String errAttrName = BindingResult.MODEL_KEY_PREFIX + MODELNAME;
			redirectAttributes.addFlashAttribute(errAttrName,errors);
			lvn = "redirect:/review/myReview";
		}
		
		
		
		return lvn;
	}
	
	@GetMapping("{reviewId}")
	public String getReviewDetail(
			@PathVariable String reviewId
			, Model model
	) {
		model.addAttribute(MODELNAME, service.readReviewDetail(reviewId));
		return "main/review/reviewDetail";
	}
	
}
