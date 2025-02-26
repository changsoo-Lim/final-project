package kr.or.ddit.resume.award.controller;

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
import kr.or.ddit.resume.award.service.AwardService;
import kr.or.ddit.vo.AwardVO;

@Controller
@RequestMapping("/award")
public class AwardController {
	
	@Inject
	AwardService serivce;
	
	@GetMapping("/awardList")
	@ResponseBody
	public List<AwardVO> cretList(Authentication authentication) {
	    String memId = ((UserDetails) authentication.getPrincipal()).getUsername();
	    return serivce.readAwardList(memId);
	}

	@GetMapping("{awardNo}")
	@ResponseBody
	public AwardVO award(@PathVariable int awardNo) {
		return serivce.readAward(awardNo);
	}

	@DeleteMapping("{awardNo}")
	@ResponseBody
	public ResponseEntity<String> del(@PathVariable int awardNo) {
		ServiceResult result = serivce.removeAward(awardNo);
		switch (result) {
		case OK:
			return ResponseEntity.ok("삭제 성공");
		default:
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 실패");
		}
	}
}
