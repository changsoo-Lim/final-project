package kr.or.ddit.logs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.logs.service.LogService;


@Controller
@RequestMapping("/admin/log")
public class LogController {
	
	@Autowired
    private LogService logService;
	
	@GetMapping
	public String logList(Model model) {
        // 로그 데이터를 서비스로부터 가져옵니다.
        model.addAttribute("logList", logService.getLogList());
        return "admin/log/logList";  // logList.jsp로 결과를 전달
    }
}
