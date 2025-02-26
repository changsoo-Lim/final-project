package kr.or.ddit.resume.qualfication.controller;

import javax.inject.Inject;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.resume.qualfication.service.QualficationService;
import kr.or.ddit.vo.QualificationVO;

@Controller
@RequestMapping("/qua")
public class QualficationController {
	
	@Inject
	QualficationService servive;
	
	@GetMapping("/quaOne")
	@ResponseBody
	public QualificationVO getUniList(Authentication authentication) {
	    String memId = ((UserDetails) authentication.getPrincipal()).getUsername();
	    return servive.readQualificationList(memId);
	}
}
