package kr.or.ddit.member.myapply.controller;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.member.myapply.service.MyApplyService;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.paging.renderer.DefaultPaginationRenderer;
import kr.or.ddit.paging.renderer.PaginationRenderer;
import kr.or.ddit.vo.ApplyVO;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping("/member/myapply")
public class MyApplyController {
	
	@Inject
	private MyApplyService service;
	
	@GetMapping
	public String myApplyList(
		Optional<Integer> page ,
		@ModelAttribute("condition") SimpleCondition simpleCondition,
		Model model,
		Authentication authentication
	) {
		String userId = ((UserDetails) authentication.getPrincipal()).getUsername();
				
		PaginationInfo paging = new PaginationInfo();
		paging.setCurrentPage(page.orElse(1));
		paging.setSimpleCondition(simpleCondition);
		
		List<ApplyVO> myApplyList = service.readMyApplyList(userId,paging);
		
		// 날짜 포맷팅 로직
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    for (ApplyVO apply : myApplyList) {
	        if (apply.getAppDate() != null) {
	            apply.setFormattedAppDate(apply.getAppDate().format(formatter)); // 포맷된 날짜 설정
	        }
	    }
		
		int totalCount = service.getTotalCount(userId,paging);
		
		PaginationRenderer renderer = new DefaultPaginationRenderer();
		String pagingHTML = renderer.renderPagination(paging, null);
		
		model.addAttribute("myApplyList", myApplyList);
		model.addAttribute("myApplyCodeList", service.readApplyCodeList());
		model.addAttribute("pagingHTML", pagingHTML);
		model.addAttribute("totalCount", totalCount);
		
		return "member/myapply/myApplyList";
	}
}
