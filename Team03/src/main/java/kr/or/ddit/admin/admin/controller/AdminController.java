package kr.or.ddit.admin.admin.controller;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.admin.admin.service.AdminService;
import kr.or.ddit.vo.EmployVO;
import kr.or.ddit.vo.UsersVO;

@RequestMapping("admin")
@Controller
public class AdminController {
	
	@Inject
	AdminService service;
	
	@GetMapping
	public String admMain() {
		
		return "admin/main/admMain";
	}
	
	@GetMapping("/monthEmploy")
	@ResponseBody
	public List<Map<String, Object>> monthEmployList(){
		
		return service.monthEmployList();
	}
	
	@GetMapping("/monthUser")
	@ResponseBody
	public List<Map<String, Object>> monthUserList(){
		
		return service.monthUserList();
	}
	
	@GetMapping("/user")
	@ResponseBody
	public List<Map<String, Object>> userList(){
		
		return service.monthUserList();
	}
	
	@GetMapping("/sido")
	@ResponseBody
	public List<Map<String, Object>> sidoList(){
		
		return service.sidoList();
	}
	
	@GetMapping("/reportList")
	@ResponseBody
	public List<Map<String, Object>> reportList(){
		
		return service.reportList();
	}
	
	@GetMapping("/reviewList")
	@ResponseBody
	public List<Map<String, Object>> reviewList(){
		
		return service.reviewList();
	}
	
	@GetMapping("/noticeList")
	@ResponseBody
	public List<Map<String, Object>> noticeList(){
		
		return service.noticeList();
	}
	
	@GetMapping("/mountAmountList")
	@ResponseBody
	public List<Map<String, Object>> mountAmountList(){
		
		return service.mountAmountList();
	}
	
	@GetMapping("/prodAmountList")
	@ResponseBody
	public List<Map<String, Object>> prodAmountList(){
		
		return service.prodAmountList();
	}
	
	@GetMapping("/totalList")
	@ResponseBody
	public List<Map<String, Object>> totalList(){
		
		return service.totalList();
	}
	
	
}
