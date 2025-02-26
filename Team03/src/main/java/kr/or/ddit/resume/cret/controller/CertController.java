package kr.or.ddit.resume.cret.controller;

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
import kr.or.ddit.resume.cret.service.CertService;
import kr.or.ddit.vo.CareerVO;
import kr.or.ddit.vo.CertVO;

@Controller
@RequestMapping("/cert")
public class CertController {
	@Inject
	CertService serivce;
	
	@GetMapping("/certList")
	@ResponseBody
	public List<CertVO> cretList(Authentication authentication) {
	    String memId = ((UserDetails) authentication.getPrincipal()).getUsername();
	    return serivce.readCertList(memId);
	}

	@GetMapping("{certNo}")
	@ResponseBody
	public CertVO cert(@PathVariable int certNo) {
		return serivce.readCert(certNo);
	}

	@DeleteMapping("{certNo}")
	@ResponseBody
	public ResponseEntity<String> del(@PathVariable int certNo) {
		ServiceResult result = serivce.removeCert(certNo);
		switch (result) {
		case OK:
			return ResponseEntity.ok("삭제 성공");
		default:
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 실패");
		}
	}
}
