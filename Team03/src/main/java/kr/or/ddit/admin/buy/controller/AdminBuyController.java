package kr.or.ddit.admin.buy.controller;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.admin.buy.service.AdminBuyService;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.paging.renderer.DefaultPaginationRenderer;
import kr.or.ddit.paging.renderer.PaginationRenderer;
import kr.or.ddit.vo.BuyVO;

@Controller
@RequestMapping("/ad/buy")
public class AdminBuyController {
	
	@Inject
	private AdminBuyService service;
	
	@GetMapping
	public String compBuyList(
			Optional<Integer> page ,
			@ModelAttribute("condition") SimpleCondition simpleCondition,
			Model model
	) {
		
		PaginationInfo paging = new PaginationInfo();
		paging.setCurrentPage(page.orElse(1));
		paging.setSimpleCondition(simpleCondition);
		
		// 여기만 12개 목록으로 출력
		String targetJSP = "admin/buy/buyList";
	    if ("admin/buy/buyList".equals(targetJSP)) { 
	        paging.setScreenSize(12); 
	    } else {
	        paging.setScreenSize(10); 
	    }
		
		// 총 게시글 수 가져오기
	    int totalCount = service.getTotalCount(paging);

	    List<BuyVO> compBuyList = service.readCompantBuyList(paging);
	    
	    PaginationRenderer renderer = new DefaultPaginationRenderer();
		String pagingHTML = renderer.renderPagination(paging, null);
		
		
		model.addAttribute("compBuyList", compBuyList);
		model.addAttribute("pagingHTML", pagingHTML);
		model.addAttribute("totalCount", totalCount);
		
		return "admin/buy/buyList";
	}

}
