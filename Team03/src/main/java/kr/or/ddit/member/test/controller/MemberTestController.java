package kr.or.ddit.member.test.controller;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.member.test.service.MemberTestService;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.paging.renderer.DefaultPaginationRenderer;
import kr.or.ddit.paging.renderer.PaginationRenderer;
import kr.or.ddit.test.candidate.vo.CandidateDTO;
import kr.or.ddit.vo.CandidateVO;
import kr.or.ddit.vo.TestVO;
import kr.or.ddit.vo.Test_AnswerVO;

@Controller
@RequestMapping("/member/test")
public class MemberTestController {
	
	@Inject
	MemberTestService memberTestService;
	
	@GetMapping("list")
	public String testList(
		  @RequestParam(value = "page", defaultValue = "1") int page
		, @RequestParam(value = "testCd", required = false) String testCd
		, @RequestParam(value = "employTitle", required = false) String employTitle
		, @RequestParam(value = "compNm", required = false) String compNm
		, @RequestParam(value = "candidateYn", required = false) String candidateYn
        , Authentication authentication
        , Model model
	){
		String userId = ((UserDetails) authentication.getPrincipal()).getUsername();
		PaginationInfo paging = new PaginationInfo();
		paging.setCurrentPage(page);
		
		Map<String, Object> searchConditions = new HashMap<>();
        searchConditions.put("testCd", testCd);
        searchConditions.put("employTitle", employTitle);
        searchConditions.put("compNm", compNm);
        searchConditions.put("candidateYn", candidateYn);
        paging.setVariousCondition(searchConditions);
		
	    List<CandidateVO> candidateList = memberTestService.readCandidateList(paging, userId);
	    
	    // 총 게시글 수 가져오기
	    int totalCount = memberTestService.readTotalCandidate(paging, userId);
	    
	    PaginationRenderer renderer = new DefaultPaginationRenderer();
		String pagingHTML = renderer.renderPagination(paging, null);
	    

	    model.addAttribute("testCd", testCd);
	    model.addAttribute("employTitle", employTitle);
	    model.addAttribute("compNm", compNm);
	    model.addAttribute("candidateYn", candidateYn);
		model.addAttribute("candidateList", candidateList);	
        model.addAttribute("pagingHTML", pagingHTML);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("testCode", memberTestService.readTestCode());
		
        return "member/test/testList";
		
	}
	
	@GetMapping("{candidateNo}")
	public String testDetail(
		  @PathVariable int candidateNo
		, Model model
	){
		CandidateDTO candidate = memberTestService.readCandidateDetail(candidateNo);
		int testNo = candidate.getTestNo();
		
		int testQuestnCount = memberTestService.readTestQuestnCount(3);
		
		model.addAttribute("testQuestnCount", testQuestnCount);
		model.addAttribute("candidate", candidate);
		model.addAttribute("testCd", memberTestService.readTestCode());
		
		return "member/test/testDetail"; 
	}
	
	@GetMapping("conduct/{candidateNo}")
	public String testConduct(
		  @PathVariable int candidateNo
		, Model model
	){
		TestVO test = new TestVO();
		
		model.addAttribute("test", test);
		model.addAttribute("candidateNo", candidateNo);
		
		return "/stackUp/member/test/testConduct"; 
	}
	
	@PostMapping("/{testNo}/submit")
    public ResponseEntity<?> submitTestAnswers(
    		  @PathVariable Integer testNo
    		, @RequestBody List<Test_AnswerVO> answers
    		, @RequestParam Integer candidateNo
    		, @RequestParam String testType
    ) {
        try {
            memberTestService.processTestAnswers(answers, candidateNo, testNo, testType);
            return ResponseEntity.ok("테스트 응시 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("정답 처리에 실패하였습니다.");
        }
    }
}
