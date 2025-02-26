package kr.or.ddit.resume.career.controller;

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
import kr.or.ddit.resume.career.service.CareerService;
import kr.or.ddit.vo.CareerVO;
import kr.or.ddit.vo.UniVO;

@Controller
@RequestMapping("/career")
public class CareerController {
	
	@Inject
	CareerService serivce;
	
	@GetMapping("/careerList")
	@ResponseBody
	public List<CareerVO> careerList(Authentication authentication) {
	    String memId = ((UserDetails) authentication.getPrincipal()).getUsername();
	    return serivce.readCareerList(memId);
	}

	@GetMapping("{careerNo}")
	@ResponseBody
	public CareerVO career(@PathVariable int careerNo) {
		return serivce.readCareer(careerNo);
	}

	@DeleteMapping("{careerNo}")
	@ResponseBody
	public ResponseEntity<String> del(@PathVariable int careerNo) {
		ServiceResult result = serivce.removeCareer(careerNo);
		switch (result) {
		case OK:
			return ResponseEntity.ok("삭제 성공");
		default:
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 실패");
		}
	}
}
