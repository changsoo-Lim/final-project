package kr.or.ddit.resume.experience.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.resume.experience.service.ExperienceService;
import kr.or.ddit.vo.ExperienceVO;

@Controller
@RequestMapping("/exp")
public class ExpController {
	
	@Inject
	ExperienceService service;
	
	@GetMapping("expList")
	@ResponseBody
	public List<ExperienceVO> expList(Authentication authentication){
		String memId = ((UserDetails)authentication.getPrincipal()).getUsername();
		return service.readExperienceList(memId);
		
	}

	@GetMapping("{expNo}")
	@ResponseBody
	public ExperienceVO exp(@PathVariable int expNo) {
		return service.readExperience(expNo);
	}

	@DeleteMapping("{expNo}")
	@ResponseBody
	public ResponseEntity<String> del(@PathVariable int expNo) {
		ServiceResult result = service.removeExperience(expNo);
		switch (result) {
		case OK:
			return ResponseEntity.ok("삭제 성공");
		default:
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 실패");
		}
	}
}
