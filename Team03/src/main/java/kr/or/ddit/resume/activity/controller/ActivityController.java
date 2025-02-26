package kr.or.ddit.resume.activity.controller;

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
import kr.or.ddit.resume.activity.service.ActivityService;
import kr.or.ddit.resume.award.service.AwardService;
import kr.or.ddit.vo.ActivityVO;
import kr.or.ddit.vo.AwardVO;
import kr.or.ddit.vo.ExperienceVO;

@Controller
@RequestMapping("/act")
public class ActivityController {
	
	@Inject
	ActivityService serivce;
	
	@GetMapping("/actList")
	@ResponseBody
	public List<ActivityVO> cretList(Authentication authentication) {
	    String memId = ((UserDetails) authentication.getPrincipal()).getUsername();
	    return serivce.readActivityList(memId);
	}

	@GetMapping("{actNo}")
	@ResponseBody
	public ActivityVO act(@PathVariable int actNo) {
		return serivce.readActivity(actNo);
	}

	@DeleteMapping("{actNo}")
	@ResponseBody
	public ResponseEntity<String> del(@PathVariable int actNo) {
		ServiceResult result = serivce.removeActivity(actNo);
		switch (result) {
		case OK:
			return ResponseEntity.ok("삭제 성공");
		default:
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 실패");
		}
	}
}
