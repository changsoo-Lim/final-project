package kr.or.ddit.member.member.controller;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.member.member.service.MemberService;
import kr.or.ddit.vo.MemberVO;

@Controller("memberController1")
@RequestMapping("/member")
public class MemberController {
	
	@Inject
	MemberService service;
	
	@GetMapping("phone/{memHp}")
	@ResponseBody
	public String selectPhoneMember(@PathVariable String memHp) {
		return service.readPhoneMember(memHp);
	}
}
