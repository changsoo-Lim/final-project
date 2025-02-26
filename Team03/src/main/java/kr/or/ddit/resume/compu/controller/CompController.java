package kr.or.ddit.resume.compu.controller;

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
import kr.or.ddit.resume.compu.service.CompService;
import kr.or.ddit.vo.CertVO;
import kr.or.ddit.vo.CompVO;

@Controller
@RequestMapping("/comp")
public class CompController {
	
	@Inject
	CompService serivce;
	
	@GetMapping("/compList")
	@ResponseBody
	public List<CompVO> cretList(Authentication authentication) {
	    String memId = ((UserDetails) authentication.getPrincipal()).getUsername();
	    return serivce.readCompList(memId);
	}

	@GetMapping("{compNo}")
	@ResponseBody
	public CompVO comp(@PathVariable int compNo) {
		return serivce.readComp(compNo);
	}

	@DeleteMapping("{compNo}")
	@ResponseBody
	public ResponseEntity<String> del(@PathVariable int compNo) {
		ServiceResult result = serivce.removeComp(compNo);
		switch (result) {
		case OK:
			return ResponseEntity.ok("삭제 성공");
		default:
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 실패");
		}
	}
}
