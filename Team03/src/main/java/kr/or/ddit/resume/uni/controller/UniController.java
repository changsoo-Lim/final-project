package kr.or.ddit.resume.uni.controller;

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
import kr.or.ddit.resume.uni.service.UniService;
import kr.or.ddit.vo.UniVO;

@Controller
@RequestMapping("/uni")
public class UniController {
	
	@Inject
	UniService serivce;
	
	@GetMapping("/uniList")
	@ResponseBody
	public List<UniVO> getUniList(Authentication authentication) {
	    String memId = ((UserDetails) authentication.getPrincipal()).getUsername();
	    return serivce.readUniList(memId); 
	}
	
	@GetMapping("{uniNo}")
	@ResponseBody
	public UniVO uni(@PathVariable int uniNo) {
		return serivce.selectUni(uniNo);
	}
	
	@DeleteMapping("{uniNo}")
	@ResponseBody
	public ResponseEntity<String> del(@PathVariable int uniNo) {
	    ServiceResult result = serivce.deleteUni(uniNo);
	    switch (result) {
	        case OK:
	            return ResponseEntity.ok("삭제 성공");
	        default:
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 실패"); // HTTP 500
	    }
	}

}
