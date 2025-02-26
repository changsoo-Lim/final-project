package kr.or.ddit.employ.employ.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.code.util.BinderBean;
import kr.or.ddit.employ.employ.service.EmployService;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.paging.renderer.DefaultPaginationRenderer;
import kr.or.ddit.paging.renderer.PaginationRenderer;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.EmployVO;
import lombok.extern.slf4j.Slf4j;

/**
 * 채용공고 MAPPER
 * 메인페이지 단건조회 : ﻿/empoly/{employNo} GET
 * 메인페이지 다건조회 : /empoly GET
 * 기업페이지 단건조회 : ﻿/empoly/{employNo}{companyId} GET
 * 기업페이지 다건조회 : ﻿/empoly/{companyId} GET
 * 공고등록 : /empoly POST
 * 공고수정 : /empoly/{employNo} PUT
 * 공고삭제 : /empoly/{employNo} DELETE (삭제여부 Y로 수정)
 */

@Slf4j
@Controller
@RequestMapping("/employ")
public class EmployController {
	PaginationInfo<EmployVO> paging = new PaginationInfo<>();
	
	public static final String MODELNAME = "employ";
	@Inject
	private EmployService employService;
	
	@ModelAttribute(MODELNAME)
	public EmployVO employ() {
		return new EmployVO();
	}
	// 채용공고 등록폼 이동
	@GetMapping("form")
	public String insertForm(Model model) {
		model.addAttribute("filterTitleList", employService.readFilterTitleList());
		model.addAttribute("filterContList", employService.readFilterContList());
		codeAddModel(model);
		return "company/employ/employForm";
	}
	// 채용공고 수정폼 이동
	@GetMapping("edit")
	public String editForm(Model model) {
		model.addAttribute("filterTitleList", employService.readFilterTitleList());
		model.addAttribute("filterContList", employService.readFilterContList());
		codeAddModel(model);
		return "company/employ/employForm";
	}
	// 채용공고 상세조회
	@GetMapping("detail/{employNo}")
	public String mainEmployDetail(@PathVariable int employNo
								, @AuthenticationPrincipal UserDetails user
								, Model model
	) {
		boolean isMember = false;
		String memId = "";
		if(user != null) {
			isMember = user.getAuthorities().stream()
					.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE02"));
		}
		if(isMember) {
			memId = user.getUsername();
		}
		try {
			model.addAttribute("employ", employService.readEmploy(employNo, memId));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "main/employ/employDetail";
	}
	// 전체 채용공고 리스트 조회
	@GetMapping("list")
	public String mainEmployList(@ModelAttribute("condition") SimpleCondition simpleCondition
								, @AuthenticationPrincipal UserDetails user, Model model
	) {
		PaginationInfo<EmployVO> paging = new PaginationInfo<>();
		paging.setCurrentPage(1);
	    paging.setSimpleCondition(simpleCondition);
	    PaginationRenderer renderer = new DefaultPaginationRenderer();
		List<EmployVO> employList = new ArrayList<>();
		boolean isMember = false;
		if(user != null) {
			isMember = user.getAuthorities().stream()
					.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE02"));
		}
		if(isMember) {
			String memId = user.getUsername();
			employList = employService.readEmployList(paging, null, memId);
		}else {
			employList = employService.readEmployList(paging, null, null);
		}
		codeAddModel(model);
		model.addAttribute("pagingHTML", renderer.renderPagination(paging, "fnPaging"));
		model.addAttribute("employList", employList);
		return "main/employ/employList";
	}
	
	// 전체 채용공고 리스트 조회(검색)
	@PostMapping("list/search")
	public String mainEmploySearchList(@RequestBody Map<String, Object> filters
									, SimpleCondition simpleCondition
									, @AuthenticationPrincipal UserDetails user, Model model
	) {

		simpleCondition.setSearchMap((Map<String, List<String>>) filters.get("searchMap"));
		simpleCondition.setSortBy((String) filters.get("sortBy"));
	    int page = Integer.parseInt(String.valueOf(filters.get("page")));
	    PaginationInfo<EmployVO> paging = new PaginationInfo<>();
		paging.setCurrentPage(page);
	    paging.setSimpleCondition(simpleCondition);
	    PaginationRenderer renderer = new DefaultPaginationRenderer();
		List<EmployVO> employList = new ArrayList<>();
		boolean isMember = false;
		if(user != null) {
			isMember = user.getAuthorities().stream()
					.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE02"));
		}
		if(isMember) {
			String memId = user.getUsername();
			employList = employService.readEmployList(paging, null, memId);
		}else {
			employList = employService.readEmployList(paging, null, null);
		}
		model.addAttribute("pagingHTML", renderer.renderPagination(paging, "fnPaging"));
		model.addAttribute("employList", employList);
		return "/stackUp/main/employ/employListArea";
	}
	
	// 해당 기업의 채용공고 리스트 조회
	@GetMapping("list/comp")
	public String compEmployList(@RequestParam(required = false, defaultValue = "1") int page
								, @ModelAttribute("condition") SimpleCondition simpleCondition
								, @AuthenticationPrincipal UserDetails user, Model model
	) {
		PaginationInfo<EmployVO> paging = new PaginationInfo<>();
		paging.setCurrentPage(page);
	    paging.setSimpleCondition(simpleCondition);
	    PaginationRenderer renderer = new DefaultPaginationRenderer();
		List<EmployVO> employList = employService.readEmployList(paging, user.getUsername(), null);
		String pagingHTML = renderer.renderPagination(paging, "fnPaging");
		
		model.addAttribute("pagingHTML", pagingHTML);
		model.addAttribute("employList", employList);
		return "company/employ/employList";
	}
	
	// ===================== 채용공고 등록 =====================
	@PostMapping
	public ResponseEntity<Map<String, String>> insertEmploy(
			@Validated(InsertGroup.class) @RequestBody EmployVO employ
			, BindingResult errors
			, @AuthenticationPrincipal UserDetails user
	) {
		
		Map<String, String> response = new HashMap<>();
		if (!errors.hasErrors()) {
			employ.setCompId(user.getUsername());
			if(employService.createEmploy(employ) > 0) {
				response.put("status", "success");
				response.put("message", "채용공고 등록 완료되었습니다.");
			}else {
				response.put("status", "fail");
				response.put("message", "채용공고 등록 실패하였습니다. 잠시 후 다시 시도해주세요.");
			}
		}else {
			String errAttrName = BindingResult.MODEL_KEY_PREFIX+MODELNAME;
			response.put("status", "error");
			response.put(errAttrName, errors.toString());
			response.put("message", "서버 오류. 잠시 후 다시 시도해주세요.");
		}
		return ResponseEntity.ok(response);
	}
	
	// ===================== 채용공고 수정 =====================
	@PutMapping("{employNo}")
	public String updateEmploy(EmployVO employ) {
		
		return "company/employ/employList";
	}
	// ===================== 채용공고 삭제 =====================
	@DeleteMapping("{employNo}")
	public String deleteEmploy() {
		
		return "company/employ/employList";
	}
	
	
	// ===================== 공통 코드 =====================
	public void codeAddModel(Model model) {
		// 경력 조건
        model.addAttribute("memberTypeList", BinderBean.getCodeList("member_type"));

        // 고용 형태
        model.addAttribute("workTypeList", BinderBean.getCodeList("work-type"));

        // 시,도 리스트
        model.addAttribute("cityList", BinderBean.getCodeList("city"));

        // 군,구 리스트
        model.addAttribute("gunguList", BinderBean.getCodeListLike("city%"));

        // 연봉 금액 리스트
        model.addAttribute("salaryRangeList", BinderBean.getCodeListLike("salary-range"));

        // 근무요일 리스트
        model.addAttribute("workdayList", BinderBean.getCodeList("WORKDAY"));

        // 근무시간 리스트
        model.addAttribute("workHourList", BinderBean.getCodeList("WORKHOUR"));

        // 채용절차 리스트
        model.addAttribute("appsttusList", BinderBean.getCodeList("APPSTTUS"));

        // 학력조건 리스트
        model.addAttribute("educationList", BinderBean.getCodeList("EDUCATION"));

        // 지원방법 리스트
        model.addAttribute("APLCList", BinderBean.getCodeList("APLC"));

        // 업종 대분류
        model.addAttribute("industry1List", BinderBean.getCodeList("industry"));

        // 업종 중분류
        model.addAttribute("industry2List", BinderBean.getCodeListLike("in__"));

        // 업종 소분류
        model.addAttribute("industry3List", BinderBean.getCodeListInSubquery("in__"));

        // 복리후생 리스트
        model.addAttribute("BFTList", BinderBean.getCodeListByCodeLike("bf%"));

	}
}
