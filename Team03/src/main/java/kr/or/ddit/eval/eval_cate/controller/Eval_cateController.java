package kr.or.ddit.eval.eval_cate.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.eval.eval_cate.dao.Eval_cateMapper;
import kr.or.ddit.eval.eval_cate.service.Eval_cateService;
import kr.or.ddit.vo.ApplyVO;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.Eval_CateVO;

@Controller
@RequestMapping("/evalCate")
public class Eval_cateController {
	
	@Inject
	Eval_cateService service;
	
	@Inject
	Eval_cateMapper mapper;
	
	@ModelAttribute("interviewList")
	public List<CodeVO> interviewList(){
		return mapper.interviewList();
	}
	
	@GetMapping("new/{fieldNo}/{vchatNo}")
	public String eval(@PathVariable int fieldNo,@PathVariable int vchatNo,Model model) {

		return "/stackUp/company/eval/evalForm";
	}
	
	@GetMapping("{fieldNo}/{vchatNo}")
	@ResponseBody
	public List<ApplyVO> memList(@PathVariable int fieldNo,@PathVariable int vchatNo) {
		return service.memList(fieldNo);
	}
	
	@PostMapping("new/{fieldNo}/{evalNo}")
	@ResponseBody
	public Map<String, Object> evalPost(@PathVariable int evalNo,@PathVariable int fieldNo, @RequestBody List<Eval_CateVO> evalCates) {
	    Map<String, Object> resp = new HashMap<>();

	    ServiceResult result = service.insertEvalCate(evalNo, evalCates);
	    switch (result) {
		case OK:
			resp.put("success", true);
	        resp.put("message", "평가 카테고리 등록에 성공했습니다.");
			break;

		default:
			resp.put("success", false);
	        resp.put("message", "평가 카테고리 등록에 실패했습니다.");
			break;
		}

	    return resp;
	}

}
