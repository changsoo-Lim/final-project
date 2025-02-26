package kr.or.ddit.resume.pref.controller;

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
import kr.or.ddit.resume.pref.service.PrefService;
import kr.or.ddit.vo.CompVO;
import kr.or.ddit.vo.PrefVO;

@Controller
@RequestMapping("/pref")
public class PrefController {
	
	@Inject
	PrefService serivce;
	
	@GetMapping("/prefList")
	@ResponseBody
	public PrefVO cretList(Authentication authentication) {
	    String memId = ((UserDetails) authentication.getPrincipal()).getUsername();
	    return serivce.readPref(memId);
	}

	@GetMapping("{prefNo}")
	@ResponseBody
	public PrefVO pref(@PathVariable int prefNo) {
		return serivce.readPrefDetail(prefNo);
	}

	@DeleteMapping("{prefNo}")
	@ResponseBody
	public ResponseEntity<String> del(@PathVariable int prefNo) {
		ServiceResult result = serivce.removePref(prefNo);
		switch (result) {
		case OK:
			return ResponseEntity.ok("삭제 성공");
		default:
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 실패");
		}
	}
}
