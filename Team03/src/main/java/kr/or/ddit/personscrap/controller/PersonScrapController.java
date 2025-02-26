package kr.or.ddit.personscrap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("personscrap")
public class PersonScrapController {

	@GetMapping
	public String list() {
		
		return "company/talent/talentScrapList";
	}
	
	@GetMapping("{scrapId}")
	public String detail() {
		
		return "compant/talent/talentDetail";
	}
	
	
}
