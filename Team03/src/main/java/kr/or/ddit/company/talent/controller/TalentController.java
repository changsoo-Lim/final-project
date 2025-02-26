package kr.or.ddit.company.talent.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.company.talent.service.TalentService;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.paging.renderer.DefaultPaginationRenderer;
import kr.or.ddit.paging.renderer.PaginationRenderer;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.EmployVO;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.Resume_ViewVO;

@Controller
@RequestMapping("talent")
public class TalentController {

	@Inject
	private TalentService service;

	@GetMapping("{talentId}/detail")
	public String detail(@PathVariable String talentId, Model model,
			@AuthenticationPrincipal UserDetails user
			) {
		Resume_ViewVO resumeViewVO = new Resume_ViewVO();
		resumeViewVO.setMemId(talentId);
		resumeViewVO.setCompId(user.getUsername());
		MemberVO member = service.readMember(resumeViewVO);
		
		List<EmployVO> empList = service.getEmploy(user.getUsername(),talentId);
		
		model.addAttribute("member", member);
		model.addAttribute("empList", empList);
		return "company/talent/talentDetail";
	}
	@GetMapping("list")
	public String searchList(Model model, @RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(value = "job", required = false) List<String> jobs,
			@RequestParam(value = "region", required = false) List<String> regions,
			@RequestParam(value = "skill", required = false) List<String> skills,
			@RequestParam(value = "language", required = false) List<String> languages,
			@RequestParam(value = "certificate", required = false) List<String> certificate,
			@RequestParam(value = "univ", required = false) List<String> univ) {

		Map<String, List<String>> searchMap = new HashMap<String, List<String>>();

		searchMap.put("jobs", jobs);
		searchMap.put("regions", regions);
		searchMap.put("skills", skills);
		searchMap.put("languages", languages);
		searchMap.put("certificate", certificate);
		searchMap.put("univ", univ);
		
		SimpleCondition simpleCondition = new SimpleCondition();
		simpleCondition.setSearchMap(searchMap);

		PaginationInfo<MemberVO> paging = new PaginationInfo<>();
		paging.setCurrentPage(page);
		paging.setSimpleCondition(simpleCondition);

		List<CodeVO> codeList = service.readCode();
		model.addAttribute("codeList", codeList);

		List<MemberVO> memList = service.searchTalent(paging);
		model.addAttribute("memList", memList);

		PaginationRenderer renderer = new DefaultPaginationRenderer();
		String pagingHTML = renderer.renderPagination(paging, null);

		model.addAttribute("pagingHTML", pagingHTML);
		
		return "company/talent/talentList";
	}

}
