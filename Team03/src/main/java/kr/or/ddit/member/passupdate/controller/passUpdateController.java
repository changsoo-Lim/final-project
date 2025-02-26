package kr.or.ddit.member.passupdate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/passUpdate")
public class passUpdateController {
	
	@GetMapping("passCheck")
	public String check() {
		return "member/passUpdate/passCheck";
	}
	
	@GetMapping("form")
	public String form() {
		return "member/passUpdate/form";
	}
	
	@GetMapping("formpost")
	public String update() {
		return "redirect:/mypage";
	}
}
