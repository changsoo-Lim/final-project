package kr.or.ddit.resume.edu.controller;

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
import kr.or.ddit.resume.award.service.AwardService;
import kr.or.ddit.resume.edu.service.EduService;
import kr.or.ddit.vo.AwardVO;
import kr.or.ddit.vo.EduVO;

@Controller
@RequestMapping("/edu")
public class EduController {
	
	@Inject
	EduService serivce;
	
	@GetMapping("/eduList")
	@ResponseBody
	public List<EduVO> cretList(Authentication authentication) {
	    String memId = ((UserDetails) authentication.getPrincipal()).getUsername();
	    return serivce.readEduList(memId);
	}

	@GetMapping("{eduNo}")
	@ResponseBody
	public EduVO edu(@PathVariable int eduNo) {
		return serivce.readEdu(eduNo);
	}

	@DeleteMapping("{eduNo}")
	@ResponseBody
	public ResponseEntity<String> del(@PathVariable int eduNo) {
		ServiceResult result = serivce.removeEdu(eduNo);
		switch (result) {
		case OK:
			return ResponseEntity.ok("삭제 성공");
		default:
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 실패");
		}
	}
}
