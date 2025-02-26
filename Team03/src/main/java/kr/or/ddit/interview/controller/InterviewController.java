package kr.or.ddit.interview.controller;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.interview.service.InterviewService;

@Controller
@RequestMapping("/interview")
public class InterviewController {
	
	@Inject
	InterviewService service; 
	
	@GetMapping
	public String list() {
		return "company/interview/interviewList";
	}
	
	@PutMapping("{intvNo}")
	public ResponseEntity<Map<String, Object>> statusUpdate(@PathVariable("intvNo") int intvNo) {
	    Map<String, Object> resp = new HashMap<>();
	    
	    ServiceResult result = service.modifiyInterview(intvNo);
	    
	    if (result == ServiceResult.OK) {
	        resp.put("success", true);
	        resp.put("message", "면접이 진행되었습니다.");
	    } else {
	        resp.put("success", false);
	        resp.put("message", "면접이 진행되지않았습니다.");
	    }

	    return ResponseEntity.ok(resp); // JSON 응답 반환
	}
}
