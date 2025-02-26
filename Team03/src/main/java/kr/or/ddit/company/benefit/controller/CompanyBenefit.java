package kr.or.ddit.company.benefit.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.company.benefit.service.BenefitService;
import kr.or.ddit.vo.Cmp_BftVO;
import kr.or.ddit.vo.CodeVO;

@Controller
@RequestMapping("benefit")
public class CompanyBenefit {

	@Inject
	private BenefitService service;

	private static final String MODELNAME = "benefit";

	@GetMapping(value = "{compId}/{category}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Cmp_BftVO> checkdBft(@PathVariable String compId, @PathVariable String category) {
		return service.readBenefitList(compId, category.replace("_SLASH_", "/"));
	}

	@GetMapping(value = "{category}/getcode", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CodeVO> getCode(@PathVariable String category) {
		return service.getCode(category.replace("_SLASH_", "/"));
	}

	@PostMapping("{compId}/bftinsert")
	@ResponseBody
	public Map<String, Object> insertBft(@PathVariable String compId, @RequestBody List<Cmp_BftVO> bftList,
			Model model) {
		
		
		for (Cmp_BftVO cmp_BftVO : bftList) {
			System.out.println(cmp_BftVO.toString());
		}
		System.out.println(compId);
		
		ServiceResult result = service.createBenefit(bftList);

		Map<String, Object> response = new HashMap<>();
		if (result == ServiceResult.OK) {
			response.put("success", true);
			response.put("message", "복리후생이 성공적으로 추가되었습니다.");
		} else {
			response.put("success", false);
			response.put("message", "복리후생 추가에 실패하였습니다.");
		}
		return response;
	}
	
	@PutMapping("{compId}/delete")
	@ResponseBody
	public Map<String, Object> deleteBft(@PathVariable String compId, @RequestBody List<String> bftNo,
			Model model) {
		
		for (String string : bftNo) {
			System.out.println(string);
		}
		
		ServiceResult result = service.removeBenfit(bftNo);

		Map<String, Object> response = new HashMap<>();
		if (result == ServiceResult.OK) {
			response.put("success", true);
			response.put("message", "복리후생이 성공적으로 수정되었습니다.");
		} else {
			response.put("success", false);
			response.put("message", "복리후생 수정에 실패하였습니다.");
		}
		return response;
	}

}
