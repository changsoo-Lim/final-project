package kr.or.ddit.company.compcontent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("compcontent")
public class CompanyContent {
	
	@GetMapping
	public String list() {
		
		return "";
	}
	
	@GetMapping("{contentno}")
	public String detail() {
		
		return "";
	}
	
}

