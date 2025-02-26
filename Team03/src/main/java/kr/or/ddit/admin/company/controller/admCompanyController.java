package kr.or.ddit.admin.company.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.admin.admin.service.AdminService;
import kr.or.ddit.admin.code.service.SearchFilterService;
import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.company.company.service.CompanyService;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.paging.renderer.DefaultPaginationRenderer;
import kr.or.ddit.paging.renderer.PaginationRenderer;
import kr.or.ddit.validate.CompanyInfoUpdateGroup;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.CompanyVO;

@Controller
@RequestMapping("admincompany")
public class admCompanyController {

	@Inject
	AdminService service;
	@Inject
	SearchFilterService searchService;
	@Inject
	CompanyService compService;

	// 기업 리스트 및 검색
	@GetMapping("list")
	public String getCompany(Model model, @ModelAttribute("condition") SimpleCondition simpleCondition,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page) {

		System.out.println(simpleCondition.toString());
		PaginationInfo<CompanyVO> paging = new PaginationInfo<>();
		paging.setCurrentPage(page);
		paging.setSimpleCondition(simpleCondition);

		List<CompanyVO> compList = service.readCompanyList(paging);
		model.addAttribute("compList", compList);

		PaginationRenderer renderer = new DefaultPaginationRenderer();
		String pagingHTML = renderer.renderPagination(paging, null);

		model.addAttribute("pagingHTML", pagingHTML);

		return "admin/company/admCompanyList";
	}

	@GetMapping("{compId}/detail")
	public String detailCompany(@PathVariable String compId, Model model) {

		model.addAttribute("company", compService.readCompany(compId));
		model.addAttribute("codeList", compService.readCode());

		return "admin/company/admCompanyDetail";
	}

	@PutMapping("updateinfo")
	@ResponseBody
	public Map<String, Object> updateInfo(@Validated(CompanyInfoUpdateGroup.class) @RequestBody CompanyVO company,
			BindingResult errors) {
		Map<String, Object> map = new HashMap();
		if (!errors.hasErrors()) {
			ServiceResult result = compService.modifyInfo(company);
			System.out.println(result);
			if (result == ServiceResult.OK) {
				map.put("success", true);
				map.put("message", "정보가 성공적으로 업데이트되었습니다.");
			} else {
				// 실패 시 응답
				map.put("success", false);
				map.put("message", "서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
			}
		} else {
			String errAttrName = BindingResult.MODEL_KEY_PREFIX + "company";
			map.put("success", false);
			map.put("message", "유효성 검증 실패");
			map.put(errAttrName, errors);
		}
		return map;
	}

	@PutMapping("updatecontent")
	@ResponseBody
	public Map<String, Object> updateContent(@RequestBody CompanyVO company) {

		Map<String, Object> response = new HashMap<>();
		// 기업 소개 내용이 없는 경우 빈 문자열 처리
		String content = company.getCompCont();
		if (content == null || content.trim().isEmpty()) {
			company.setCompCont(""); // null 값은 빈 문자열로 설정
		}
		ServiceResult result = compService.modifyCompContent(company);
		if (result == ServiceResult.OK) {
			response.put("success", true);
			response.put("message", "기업 소개가 성공적으로 업데이트되었습니다.");
		} else {
			response.put("success", false);
			response.put("message", "업데이트에 실패했습니다.");
		}
		return response;
	}

	@PostMapping("updatelogo")
	@ResponseBody
	public Map<String, Object> updateLogo(CompanyVO company) {
		Map<String, Object> map = new HashMap<>();
		System.out.println(company.getCompId());
		ServiceResult result = compService.updateCompanyImage(company);

		switch (result) {
		case OK:
			map.put("status", "success");
			map.put("message", "등록 성공");
			break;

		case FAIL:
			map.put("status", "fail");
			map.put("message", "등록 실패");
			break;

		default:
			map.put("status", "error");
			map.put("message", "등록 오류");
			break;
		}
		return map;
	}

}