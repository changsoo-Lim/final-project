package kr.or.ddit.resume.work_cond.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.resume.work_cond.service.Work_CondService;
import kr.or.ddit.vo.Work_CityVO;
import kr.or.ddit.vo.Work_CondVO;

@Controller
@RequestMapping
public class WorkController {
	
	@Inject
	Work_CondService workService;
	
	@GetMapping("/workCity/workCityList")
	@ResponseBody
	public List<Work_CityVO> workCityList(Authentication authentication) {
	    String memId = ((UserDetails) authentication.getPrincipal()).getUsername();

	    Work_CondVO workCond = workService.selectWorkCondOne(memId);

	    // Work_CityVO 리스트 반환
	    return Optional.ofNullable(workCond)
	        .map(Work_CondVO::getWorkCondNo) // workCondNo 추출
	        .map(workCondNo -> workService.selectWorkCityList(workCondNo)) // workCondNo로 workCityList 조회
	        .orElseGet(ArrayList::new); // 없으면 빈 리스트 반환
	}

	
	@GetMapping("/workCtiy/{workCityNo}")
	@ResponseBody
	public List<Work_CityVO> workCity(@PathVariable int workCityNo) {
		return workService.readWorkCity(workCityNo);
	}
	
	@DeleteMapping("/workCity/{workCityNo}")
	@ResponseBody
	public ResponseEntity<String> workCityDel(@PathVariable int workCityNo) {
	    ServiceResult result = workService.deleteWorkCity(workCityNo);
	    switch (result) {
	        case OK:
	            return ResponseEntity.ok("삭제 성공");
	        default:
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 실패"); // HTTP 500
	    }
	}
	
	@GetMapping("/workCond/workCondList")
	@ResponseBody
	public Work_CondVO cretList(Authentication authentication) {
	    String memId = ((UserDetails) authentication.getPrincipal()).getUsername();
	    return workService.selectWorkCondOne(memId);
	}
	
	@GetMapping("/workCond/{workCondNo}")
	@ResponseBody
	public Work_CondVO workCond(@PathVariable int workCondNo) {
		return workService.readWorkCond(workCondNo);
	}
	
	@DeleteMapping("/workCond/{workCondNo}")
	@ResponseBody
	public ResponseEntity<String> workCondDel(@PathVariable int workCondNo) {
		ServiceResult result = workService.removeWorkCond(workCondNo);
		switch (result) {
		case OK:
			return ResponseEntity.ok("삭제 성공");
		default:
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 실패"); // HTTP 500
		}
	}
}
