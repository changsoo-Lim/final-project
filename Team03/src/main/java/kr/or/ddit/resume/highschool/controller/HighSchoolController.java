package kr.or.ddit.resume.highschool.controller;

import javax.inject.Inject;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.resume.highschool.service.HighSchoolService;
import kr.or.ddit.vo.HighSchoolVO;

@Controller
@RequestMapping("/highSchool")
public class HighSchoolController {
	
	@Inject
	private HighSchoolService service;
	
	@GetMapping
	@ResponseBody
	public HighSchoolVO highSchool(Authentication authentication) {
	    String memId = ((UserDetails) authentication.getPrincipal()).getUsername();
	    return service.readHighshcool(memId);
	}
}
