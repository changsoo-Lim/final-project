package kr.or.ddit.admin.test.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import kr.or.ddit.admin.test.service.AdminTestService;
import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.paging.renderer.DefaultPaginationRenderer;
import kr.or.ddit.paging.renderer.PaginationRenderer;
import kr.or.ddit.test.test.vo.TestVOWrapper;
import kr.or.ddit.vo.TestVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/admin/test")
public class AdminTestController {
	
	@Inject
	AdminTestService testService;
	
	@GetMapping("list")
	public String adminTestList(
			@RequestParam(value = "testCd", defaultValue = "TE01") String testCd,
			Optional<Integer> page,
            @ModelAttribute("condition") SimpleCondition simpleCondition,
            Model model
	) {
		PaginationInfo paging = new PaginationInfo();
		paging.setCurrentPage(page.orElse(1));
		paging.setSimpleCondition(simpleCondition);
		
	    List<TestVO> testList = testService.readTestList(paging, testCd);
	    
	    // 총 게시글 수 가져오기
	    int totalCount = testService.readTotalTest(paging, testCd);
	    
	    PaginationRenderer renderer = new DefaultPaginationRenderer();
		String pagingHTML = renderer.renderPagination(paging, null);
	    
		model.addAttribute("testList", testList);	
        model.addAttribute("pagingHTML", pagingHTML);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("testCd", testCd);
        model.addAttribute("testCode", testService.readTestCode());
		
	    return "admin/test/testList";
	}
	
	@GetMapping("form")
	public String form( Model model	) {
		model.addAttribute("testCode", testService.readTestCode());
		
		return "admin/test/testForm";
	}
	
	@PostMapping("insert")
	public String insertTest(
		@ModelAttribute TestVOWrapper testVOWrapper
		, Authentication authentication
	) {
		log.info("TestVOWrapper 데이터: {}", testVOWrapper);
		log.info("TestQuestnList: {}", testVOWrapper.getTestQuestnList());
		String userId = ((UserDetails) authentication.getPrincipal()).getUsername();
		testVOWrapper.getTest().setCompId(userId);
		ServiceResult result = testService.createTest(testVOWrapper);
		log.info(testVOWrapper.toString());
		
		if (result == ServiceResult.OK) {
	        return "redirect:/admin/test/list";
	    } else {
	    	return "redirect:/admin/test/form";
	    }
	}
	
	@GetMapping("{testNo}")
	public String edit(
			@PathVariable int testNo
			, Model model
	) throws JsonProcessingException {
		TestVO test = testService.readTest(testNo);
	    if (test == null) {
	        throw new IllegalStateException("해당 테스트를 찾을 수 없습니다.");
	    }
		
		TestVOWrapper testVOWrapper = new TestVOWrapper();
		testVOWrapper.setTest(test);
		testVOWrapper.setTestQuestnList(test.getTestQuestnList());
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String testQuestnList = objectMapper.writeValueAsString(testVOWrapper.getTestQuestnList());
		
		model.addAttribute("testQuestnList", testQuestnList);
		model.addAttribute("test", testVOWrapper.getTest());
		model.addAttribute("testCode", testService.readTestCode());
		
		return "admin/test/testEdit";
	}
	
	
	@PutMapping("{testNo}")
	public String update(
		  @PathVariable int testNo
		, @ModelAttribute TestVOWrapper testWrapper
		, Authentication authentication
	) {
		log.info("업데이트 요청 데이터: {}", testWrapper);
		
	    String userId = ((UserDetails) authentication.getPrincipal()).getUsername();
	    testWrapper.getTest().setCompId(userId);
	    testWrapper.getTest().setTestNo(testNo);

	    ServiceResult result = testService.modifyTest(testWrapper);
	    log.info(result.toString()); 
	    if (result == ServiceResult.OK) {
	    	// 성공
	        return "redirect:/admin/test/list";
	    } else {
	    	// 에러 발생 시 다시 수정 페이지로 이동
	        return "redirect:/admin/test/" + testNo;
	    }
		
//		return "admin/test/testDetail";
	}
	
	@DeleteMapping("{testNo}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> deleteTest(
			@PathVariable int testNo
	) {
		ServiceResult result = testService.removeTest(testNo);
	    if (result == ServiceResult.OK) {
	        // 성공 시 JSON 응답
	        return ResponseEntity.ok(Collections.singletonMap("success", true));
	    } else {
	        // 실패 시 JSON 응답
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body(Collections.singletonMap("success", false));
	    }
	}
	
	@DeleteMapping("deleteMultiple")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> deleteMultiple(
		  @RequestBody List<Integer> testNo
	) {
		if (testNo == null || testNo.isEmpty()) {
	        return ResponseEntity.badRequest()
	                             .body(Collections.singletonMap("success", false));
	    }

	    log.info("삭제 요청된 시험 번호: {}", testNo);
	    
        ServiceResult result = testService.removeAdminTestList(testNo);
        if (result == ServiceResult.OK) {
        	return ResponseEntity.ok(Collections.singletonMap("success", true));
        } else {
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("success", false));
        }
	}
}
