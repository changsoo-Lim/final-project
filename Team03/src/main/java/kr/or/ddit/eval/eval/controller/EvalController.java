package kr.or.ddit.eval.eval.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.eval.eval.service.EvalService;
import kr.or.ddit.vo.EvalVO;
import kr.or.ddit.vo.Eval_CateVO;

@Controller
@RequestMapping("/eval")
public class EvalController {

	@Inject
	private EvalService service;
	
	@ModelAttribute("eval")
	public EvalVO vchat() {
		return new EvalVO();
	}
	
	@PostMapping
	@ResponseBody
	public Map<String, Object> evalPost(@RequestBody EvalVO evalVO) {
	    Map<String, Object> resp = new HashMap<>();
	    ServiceResult result = service.createEval(evalVO);
	    switch (result) {
		case OK:
			resp.put("success", true);
	        resp.put("message", "평가 카테고리 등록에 성공했습니다.");
	        resp.put("evalNo", evalVO.getEvalNo());
			break;

		default:
			resp.put("success", false);
	        resp.put("message", "평가 카테고리 등록에 실패했습니다.");
			break;
		}

	    return resp;
	}
}
