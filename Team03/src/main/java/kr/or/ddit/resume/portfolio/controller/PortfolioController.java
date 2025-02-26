package kr.or.ddit.resume.portfolio.controller;

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
import kr.or.ddit.resume.portfolio.service.PortfolioService;
import kr.or.ddit.vo.PortfolioVO;
import kr.or.ddit.vo.UniVO;

@Controller
@RequestMapping("/port")
public class PortfolioController {

	@Inject
	PortfolioService serivce;
	
	@GetMapping("/portList")
	@ResponseBody
	public List<PortfolioVO> getUniList(Authentication authentication) {
	    String memId = ((UserDetails) authentication.getPrincipal()).getUsername();
	    return serivce.readPortfolioList(memId);
	}
	
	@DeleteMapping("{portNo}")
	@ResponseBody
	public ResponseEntity<String> del(@PathVariable int portNo) {
		ServiceResult result = serivce.removePortfolio(portNo);
		switch (result) {
		case OK:
			return ResponseEntity.ok("삭제 성공");
		default:
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 실패");
		}
	}
}
