package kr.or.ddit.intro.intro_detail.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.intro.intro_detail.service.Intro_DetailService;

@Controller
@RequestMapping("/introDetail")
public class IntroDetailController {
	
	@Inject
	Intro_DetailService service;
	
	@DeleteMapping("{introDetailNo}")
	@ResponseBody
	public String del(@PathVariable int introDetailNo,RedirectAttributes redirectAttributes) {
		
		String lvn = null;
		ServiceResult result = service.removeIntroDetail(introDetailNo);
		switch (result) {
			case OK:
				lvn = "redirect:/intro";
				break;
				
			default:
				redirectAttributes.addFlashAttribute("message", "삭제 실패 !");
				lvn = "redirect:/intro";
				break;
		}
		return lvn;
	}
}
