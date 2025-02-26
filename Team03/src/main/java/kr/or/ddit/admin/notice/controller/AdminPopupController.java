package kr.or.ddit.admin.notice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/popup")
public class AdminPopupController {
	@GetMapping
	public String list() {
		
		return "admin/popup/popupList";
	}
	
	@GetMapping("{noticeId}")
	public String detail() {
		
		return "admin/popup/popupDetail";
	}
	
	@GetMapping("/popupForm")
	public String insert() {
		
		return "admin/popup/popupForm";
	}
	
	@GetMapping("/popupEdit")
	public String update() {
		
		return "admin/popup/popupEdit";
	}
}
