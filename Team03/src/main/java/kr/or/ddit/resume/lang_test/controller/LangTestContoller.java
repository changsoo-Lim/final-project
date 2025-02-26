package kr.or.ddit.resume.lang_test.controller;

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
import kr.or.ddit.resume.lang_test.service.Lang_TestService;
import kr.or.ddit.vo.Lang_TestVO;

@Controller
@RequestMapping("/langTest")
public class LangTestContoller {
	
	@Inject
	Lang_TestService serivce;
	
	@GetMapping("/langTestList")
	@ResponseBody
	public List<Lang_TestVO> cretList(Authentication authentication) {
	    String memId = ((UserDetails) authentication.getPrincipal()).getUsername();
	    return serivce.readLangtestList(memId);
	}

	@GetMapping("{langTestNo}")
	@ResponseBody
	public Lang_TestVO langTest(@PathVariable int langTestNo) {
		return serivce.readLangtest(langTestNo);
	}

	@DeleteMapping("{langTestNo}")
	@ResponseBody
	public ResponseEntity<String> del(@PathVariable int langTestNo) {
		ServiceResult result = serivce.removeLangTest(langTestNo);
		switch (result) {
		case OK:
			return ResponseEntity.ok("삭제 성공");
		default:
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 실패");
		}
	}
}
