package kr.or.ddit.company.company.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.annotation.MultipartConfig;

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
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.company.benefit.service.BenefitService;
import kr.or.ddit.company.company.service.CompanyService;
import kr.or.ddit.json.JsonService;
import kr.or.ddit.validate.CompanyInfoUpdateGroup;
import kr.or.ddit.vo.Cmp_BftVO;
import kr.or.ddit.vo.CompanyVO;
import kr.or.ddit.vo.EmployVO;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.ProjectVO;
import kr.or.ddit.vo.ReviewVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/company")
@Slf4j
public class CompanyController {

	@Inject
	private CompanyService service;

	@Inject
	private JsonService jsonService;

	private static final String MODELNAME = "company";

	// xml 파일 경로 설정

	// final String filePath =
	// "\\\\192.168.146.56\\stackUp\\resources\\CompanyNum.xml";

	final String filePath = "C:\\Users\\JaeHwang\\Desktop\\CompanyNum.xml";

	@ModelAttribute(MODELNAME)
	public CompanyVO compVO() {
		return new CompanyVO();
	}

	@GetMapping("{compId}")
	public String memberCompanyMain(@PathVariable("compId") String compId, Model model) {

		File file = new File(filePath);

		if (file.exists()) {
			log.info("파일이 존재합니다: {}", filePath);
		} else {
			log.info("파일이 존재하지 않습니다: {}", filePath);
		}
		// xml 파일 경로 설정
		String json = "";
		// json 파싱
		// XML -> JSON 변환
		try {
			// JsonService를 통해 XML 데이터를 Map으로 변환
			json = jsonService.parseXmlToJson(file.getPath());
		} catch (Exception e) {
			// 예외 발생 시 로그 출력 및 처리
			log.error(e.getMessage());
			e.printStackTrace();
		}

		CompanyVO comp = service.readCompany(compId);
		List<EmployVO> employ = service.selectEmpList(compId);
		List<Cmp_BftVO> bftList = service.selectBenefitList(compId);
		List<ReviewVO> reList = service.selectReviewList(compId);
		model.addAttribute("company", comp);
		model.addAttribute("bftList", bftList);
		model.addAttribute("empList", employ);
		model.addAttribute("companyData", json);
		model.addAttribute("reList", reList);

		return "main/company/memberCompMain";
	}

	@GetMapping
	public String companyMain(@AuthenticationPrincipal UserDetails user, Model model) {

		File file = new File(filePath);

		if (file.exists()) {
			log.info("파일이 존재합니다: {}", filePath);
		} else {
			log.info("파일이 존재하지 않습니다: {}", filePath);
		}
		// xml 파일 경로 설정
		String json = "";
		// json 파싱
		// XML -> JSON 변환
		try {
			// JsonService를 통해 XML 데이터를 Map으로 변환
			json = jsonService.parseXmlToJson(file.getPath());
		} catch (Exception e) {
			// 예외 발생 시 로그 출력 및 처리
			log.error(e.getMessage());
			e.printStackTrace();
		}

		CompanyVO comp = service.readCompany(user.getUsername());
		List<EmployVO> employ = service.selectEmpList(user.getUsername());
		List<ProjectVO> proVO = service.selectProjectList(user.getUsername());
		model.addAttribute("company", comp);
		model.addAttribute("empList", employ);
		model.addAttribute("companyData", json);
		model.addAttribute("proList", proVO);

		return "company/main/companyMain";
	}

	@GetMapping("form")
	public String detail(@AuthenticationPrincipal UserDetails user, Model model) {

		model.addAttribute(MODELNAME, service.readCompany(user.getUsername()));
		model.addAttribute("codeList", service.readCode());

		return "company/main/companyForm";
	}

	@PutMapping("updateinfo")
	@ResponseBody
	public Map<String, Object> updateInfo(@AuthenticationPrincipal UserDetails user,
			@Validated(CompanyInfoUpdateGroup.class) @RequestBody CompanyVO company, BindingResult errors) {
		company.setCompId(user.getUsername());

		Map<String, Object> map = new HashMap<>();

		if (!errors.hasErrors()) {
			ServiceResult result = service.modifyInfo(company);
			if (result == ServiceResult.OK) {
				map.put("success", true);
				map.put("message", "정보가 성공적으로 업데이트되었습니다.");
			} else {
				// 실패 시 응답
				map.put("success", false);
				map.put("message", "서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");

			}
		} else {
			String errAttrName = BindingResult.MODEL_KEY_PREFIX + MODELNAME;
			map.put("success", false);
			map.put("message", "유효성 검증 실패");
			map.put(errAttrName, errors);
		}
		return map;
	}

	@PutMapping("updatecontent")
	@ResponseBody
	public Map<String, Object> updateContent(@AuthenticationPrincipal UserDetails user,
			@RequestBody CompanyVO company) {
		company.setCompId(user.getUsername());

		Map<String, Object> response = new HashMap<>();
		// 기업 소개 내용이 없는 경우 빈 문자열 처리
		String content = company.getCompCont();
		if (content == null || content.trim().isEmpty()) {
			company.setCompCont(""); // null 값은 빈 문자열로 설정
		}

		ServiceResult result = service.modifyCompContent(company);

		if (result == ServiceResult.OK) {
			response.put("success", true);
			response.put("message", "기업 소개가 성공적으로 업데이트되었습니다.");
		} else {
			response.put("success", false);
			response.put("message", "업데이트에 실패했습니다.");
		}
		return response;
	}

	@GetMapping("/phone/{compMobile}")
	@ResponseBody
	public String selectPhoneCompany(@PathVariable String compMobile) {
		return service.readCompMobile(compMobile);
	}

	@PostMapping("updatelogo")
	@ResponseBody
	public Map<String, Object> updateLogo(CompanyVO company, @AuthenticationPrincipal UserDetails user) {
		Map<String, Object> map = new HashMap<>();
		company.setCompId(user.getUsername());
		ServiceResult result = service.updateCompanyImage(company);

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
