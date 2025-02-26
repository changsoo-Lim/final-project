package kr.or.ddit.member.info.controller;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.member.info.service.MemberInfoService;
import kr.or.ddit.users.service.UsersService;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.UsersVO;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/member/info")
public class MemberInfoController {
	public static final String MODELNAME = "member";
	@Inject
	MemberInfoService service;
	
	@Inject
	UsersService userService;
	
	@Inject
	PasswordEncoder encoder;
	
	@GetMapping("/{pageName}/passCheck")
	public String form(
			@PathVariable String pageName
			, Model model
	) {
		model.addAttribute("pageName", pageName);	
		
		return "member/info/passCheck";
	}
	
	@PostMapping("/{pageName}/passCheck")
	@ResponseBody
	public Map<String, Object> check(
			Authentication authentication
			, @PathVariable String pageName
			, @RequestBody Map<String, String> requestBody
			, HttpSession session
	) {
		Map<String, Object> resultMap = new HashMap<>();
		String result = null;
		
		String userId = ((UserDetails) authentication.getPrincipal()).getUsername();
		UsersVO user = userService.readPass(userId);
		String encodedPassword = user.getUserPass();
		String userPass = requestBody.get("userPass");
		
		boolean isPassCheck = userService.checkPassword(userPass, encodedPassword);
		result = String.valueOf(isPassCheck);
		
		if(isPassCheck) {
			session.setAttribute("authChecked", true);
		}
		
		resultMap.put("pageName", pageName);
		resultMap.put("result", result);
		
		return resultMap;
	}
	
	@GetMapping("/accessPage/{pageName}")
	public String accessPage(
	        @PathVariable String pageName,
	        HttpSession session
	) {
	    Boolean authChecked = (Boolean) session.getAttribute("authChecked");

	    if (authChecked == null || !authChecked) {
	        return "redirect:/index.do";
	    }

	    session.removeAttribute("authChecked");

	    return "forward:/member/info/" + pageName;
	}
	
	@GetMapping("infoForm")
	public String infoForm(
			Authentication authentication			
			, Model model
	) { 
		String userId = ((UserDetails) authentication.getPrincipal()).getUsername();
		model.addAttribute(MODELNAME, service.readMemberInfo(userId));
		model.addAttribute("codeList", service.readMemberInfoCodeList());
		
		return "member/info/infoForm";
	}
	
	@PutMapping("updateInfo")
	@ResponseBody
	public Map<String, Object> updateInfo(
			@ModelAttribute(MODELNAME) MemberVO member
	) {
		Map<String, Object> response = new HashMap<>();
		String result = "false";
		ServiceResult serviceResult = service.modifyMemberInfo(member);
		if(serviceResult == ServiceResult.OK) {
			result = "ok";
		} else if (serviceResult == ServiceResult.FAIL) {
			result = "fail";
		}
		
		response.put("result", result);
		
		return response;
	}
	
	@GetMapping("emailCheck/{memEmail}")
	public Map<String, Object> emailCheck(@PathVariable String memEmail) {
		Map<String, Object> response = new HashMap<>();
		
		response.put("result", service.readMemberEmailCheck(memEmail));
		
		return response;
	}
	
	@GetMapping("phoneCheck/{memHp}")
	public Map<String, Object> phoneCheck(@PathVariable String memHp) {
		Map<String, Object> response = new HashMap<>();
		
		response.put("result", service.readMemberPhoneCheck(memHp));
		
		return response;
	}
	
	@GetMapping("passForm")
	public String passForm() {
		return "member/info/passForm";
	}
	
	@PutMapping("updatePass")
	@ResponseBody
	public String updatePass(
			Authentication authentication
			, String userPass
	) {
		String result = "false";
		String userId = ((UserDetails) authentication.getPrincipal()).getUsername();
		UsersVO user = new UsersVO();
		user.setUserId(userId);
		String encodeUserPass = encoder.encode(userPass);
		user.setUserPass(encodeUserPass);
		ServiceResult serviceResult = userService.modifyPassword(user);
		if(serviceResult == ServiceResult.OK) {
			result = "ok";
		} else if (serviceResult == ServiceResult.FAIL) {
			result = "fail";
		}
		
		return result;
	}
	
	@GetMapping("deleteForm")
	public String form() {
		return "member/info/deleteForm";
	}
	
	@DeleteMapping("deleteUser")
	@ResponseBody
	public String deleteUser(
			Authentication authentication
			, @RequestBody Map<String, String> data
	){
		String userCd = data.get("userCd");
		UsersVO user = new UsersVO();
		user.setUserCd(userCd);
		user.setUserId(((UserDetails) authentication.getPrincipal()).getUsername());
		ServiceResult serviceResult = userService.removeUser(user);
		String result = "false";
		if(serviceResult == ServiceResult.OK) {
			result = "ok";
		} else {
			result = "fail";
		}
		
		return result;
	}
}
