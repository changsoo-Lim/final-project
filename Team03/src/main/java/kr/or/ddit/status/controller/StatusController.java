package kr.or.ddit.status.controller;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.status.StatusDTO;
import kr.or.ddit.status.service.StatusService;
import kr.or.ddit.vo.StatusVO;

@Controller
@RequestMapping("/status")
public class StatusController {
	
	@Inject
	StatusService service;
	
	@GetMapping
	public String view(Authentication authentication,Model model) {
		
		if(authentication==null) {
			return "redirect:/login";
		}
		String memId = ((UserDetails) authentication.getPrincipal()).getUsername();
		
		StatusVO status = service.readStatus(memId);
		
		model.addAttribute("status", status);
		
		return "member/status/statusView";
	}
	
	@PutMapping
	public ResponseEntity<String> updateNotification(Authentication authentication,@RequestBody StatusDTO statusDTO) {
		String memId = ((UserDetails) authentication.getPrincipal()).getUsername();
		String type = statusDTO.getType();
		String state = statusDTO.getState();
        ServiceResult result = service.modifiyStatus(memId, type, state);
        
        switch (result) {
		case OK:
			return ResponseEntity.ok("업데이트 성공");

		default:
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("업데이트 실패");
		}
        
    }
}
