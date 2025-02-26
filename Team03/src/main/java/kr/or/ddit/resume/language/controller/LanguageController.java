package kr.or.ddit.resume.language.controller;

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
import kr.or.ddit.resume.language.service.LanguageService;
import kr.or.ddit.vo.LanguageVO;

@Controller
@RequestMapping("/lang")
public class LanguageController {
	
	@Inject
	LanguageService serivce;
	
	@GetMapping("/langList")
	@ResponseBody
	public List<LanguageVO> cretList(Authentication authentication) {
	    String memId = ((UserDetails) authentication.getPrincipal()).getUsername();
	    return serivce.readLanguageList(memId);
	}

	@GetMapping("{langNo}")
	@ResponseBody
	public LanguageVO language(@PathVariable int langNo) {
		return serivce.readLanguage(langNo);
	}

	@DeleteMapping("{langNo}")
	@ResponseBody
	public ResponseEntity<String> del(@PathVariable int langNo) {
		ServiceResult result = serivce.removeLanguage(langNo);
		switch (result) {
		case OK:
			return ResponseEntity.ok("삭제 성공");
		default:
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 실패");
		}
	}
}
