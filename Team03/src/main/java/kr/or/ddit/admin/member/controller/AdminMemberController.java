package kr.or.ddit.admin.member.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.admin.member.service.AdminMemberService;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.paging.renderer.DefaultPaginationRenderer;
import kr.or.ddit.paging.renderer.PaginationRenderer;
import kr.or.ddit.vo.UsersVO;

@Controller
@RequestMapping("adminmember")
public class AdminMemberController {
	
	@Inject
	private AdminMemberService service;
	
	@GetMapping("list")
	public String getMember(Model model, @ModelAttribute("condition") SimpleCondition simpleCondition
			,@RequestParam(value = "page", required = false, defaultValue = "1") int page ) {
		
		PaginationInfo<UsersVO> paging = new PaginationInfo<UsersVO>();
		paging.setCurrentPage(page);
		paging.setSimpleCondition(simpleCondition);
		
		List<UsersVO> memList = service.readMember(paging);
		
		model.addAttribute("memList", memList);
		
		PaginationRenderer renderer = new DefaultPaginationRenderer();
		String pagingHTML = renderer.renderPagination(paging, null);

		model.addAttribute("pagingHTML", pagingHTML);
		
		return "admin/member/adminMemberList";

		
	}
	

}
 