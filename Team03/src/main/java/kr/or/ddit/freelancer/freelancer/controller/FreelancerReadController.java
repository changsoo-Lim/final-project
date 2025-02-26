package kr.or.ddit.freelancer.freelancer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.company.project.service.CompanyProjectService;
import kr.or.ddit.freelancer.freelancer.service.FreelancerService;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.paging.renderer.DefaultPaginationRenderer;
import kr.or.ddit.paging.renderer.PaginationRenderer;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.FreelancerVO;
import kr.or.ddit.vo.ProjectVO;
import lombok.extern.slf4j.Slf4j;

/**
 * 회원 프리랜서 메인 : /freelancer/freelancerMain GET
 * 등록폼 이동 : /freelancer/freelancerForm GET
 * 수정폼 이동 : /freelancer/freelancerEdit GET
 * 단건조회 : /freelancer/{memId} GET
 * 다건조회 : /freelancer GET
 * 등록 : /freelancer POST
 * 수정 : /freelancer{memId} POST
 * 삭제 : /freelancer{memId} DELETE
 */
@Slf4j
@Controller
@RequestMapping("/freelancer")
public class FreelancerReadController {
	
	public static final String MODELNAME = "freelancer";
	
	@Autowired
	private FreelancerService service;
	@Autowired
	private CompanyProjectService projectService;
	
	@ModelAttribute("codeList")
	public List<CodeVO> codeList() {
		List<CodeVO> codeList = service.selectCodeList();
		return codeList;
	}
	
	@GetMapping("main")
	public String main() {
		return "main/freelancer/freelancerMain";
	}
	
	@GetMapping("list")
	public String list(
		@RequestParam(required = false, defaultValue = "1") int page
		, @ModelAttribute("condition") SimpleCondition simpleCondition
		, Model model
	) {
		PaginationInfo<FreelancerVO> paging = new PaginationInfo<>();
		paging.setCurrentPage(page);
		paging.setSimpleCondition(simpleCondition);
		log.info("simpleCondition : {}", simpleCondition.toString());
		model.addAttribute("freelancerList", service.readFreelancerList(paging));
		PaginationRenderer renderer = new DefaultPaginationRenderer();
		model.addAttribute("pagingHTML", renderer.renderPagination(paging, "fnPaging"));
		return "main/freelancer/freelancerList";
	}
	@GetMapping("{memId}")
	public String detail(@PathVariable String memId
						, @AuthenticationPrincipal UserDetails user
						, Model model
	) {
		// 로그인되어 있는지, 그리고 권한이 ROLE03(기업)인지 판별
	    if (user != null) {
	        boolean isCompany = user.getAuthorities().stream()
	                                .anyMatch(auth -> "ROLE03".equals(auth.getAuthority()));
	        if (isCompany) {
	            List<ProjectVO> projectList = projectService.selectProjectListOneComp(null, user.getUsername());
	            model.addAttribute("projectList", projectList);
	        }
	    }
		FreelancerVO freelancer = service.readFreelancer(memId);
		List<CodeVO> codeList = service.selectCodeList();
		model.addAttribute(MODELNAME, freelancer);
		model.addAttribute("codeList", codeList);
		return "main/freelancer/freelancerDetail";
	}
	
 }
