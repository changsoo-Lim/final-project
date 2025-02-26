package kr.or.ddit.resume_view.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.renderer.DefaultPaginationRenderer;
import kr.or.ddit.paging.renderer.PaginationRenderer;
import kr.or.ddit.resume_view.service.Resume_ViewService;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.Resume_ViewVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("resumeview")
@Slf4j
public class Resume_ViewController {
	
	@Inject
	private Resume_ViewService service;
	
	@GetMapping
	public String resume_Viewselect(Model model
			,@AuthenticationPrincipal UserDetails user
			,@RequestParam(required = false, defaultValue = "1") int page ) {
		
		PaginationInfo<Resume_ViewVO> pagination = new PaginationInfo<Resume_ViewVO>();
		
		pagination.setCurrentPage(page);
		
		List<MemberVO> talentList= service.resumeViewSelectList(pagination, user.getUsername()); 

		PaginationRenderer renderer = new DefaultPaginationRenderer();
		String pagingHTML = renderer.renderPagination(pagination, null);
		
			
		model.addAttribute("pagingHTML", pagingHTML);
		model.addAttribute("memList", talentList);
		
		return "company/resumeview/resumeviewList";
	}
}
