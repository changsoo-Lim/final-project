package kr.or.ddit.member.member.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.employ.field.service.FieldService;
import kr.or.ddit.member.member.service.MypageService;
import kr.or.ddit.member.recommend.service.RecommendService;
import kr.or.ddit.vo.FieldVO;

@Controller
@RequestMapping("/member/mypage")
public class MypageController<K> {
	
	@Autowired
    private RecommendService service;
	@Inject
	private FieldService fieldService;
	@Inject
	private MypageService myPageService;
	
    @GetMapping
    public String mypage(
    	Authentication authentication,
    	Model model
    		
    ) {
    	String userId = ((UserDetails) authentication.getPrincipal()).getUsername();
    	
    	model.addAttribute("memberData", myPageService.readMemberData(userId));
    	model.addAttribute("salaryCodeList", myPageService.readSalaryCodeList());
    	model.addAttribute("compCodeList", myPageService.readCompCodeList());
    	model.addAttribute("workTypeCodeList", myPageService.readWorkTypeCodeList());
    	model.addAttribute("classifyCodeList", myPageService.readClassifyCodeList());
    	model.addAttribute("totalApply", myPageService.readTotalApply(userId));
    	model.addAttribute("totalPosition", myPageService.readTotalPosition(userId));
    	model.addAttribute("totalCandidate", myPageService.readTotalCandidate(userId));
    	model.addAttribute("totalApplyOpen", myPageService.readTotalApplyOpen(userId));
    	model.addAttribute("totalEmployScrap", myPageService.readTotalEmployScrap(userId));
    	model.addAttribute("totalInterComp", myPageService.readTotalInterComp(userId));
    	
    	
    	
	
	 //파이썬 연동 기업명, 기업정보 등등 처음 찍어야할 값도 담아서 보내기 
  	Map<String, Object> recommend = service.getRecommendations(userId);
	 
	 List<Integer> idList = new ArrayList<>();
	 
	 // "nearest_neighbors" 값 가져오기 
	 List<Map<String, Object>> nearestNeighbors = (List<Map<String, Object>>)recommend.get("nearest_neighbors");
	 
	 if (nearestNeighbors != null) { for (Map<String, Object> neighbor : nearestNeighbors) {
		 Map<String, Object> target = (Map<String, Object>) neighbor.get("target");
	 
	 if (target != null) { Object id = target.get("id"); if (id != null) {
	 idList.add(Integer.parseInt(id.toString())); } } } }
	 System.out.println(idList.toString()); List<FieldVO> fieldList =
	 fieldService.selectRecommendField(idList);
	 
	 model.addAttribute("fieldList", fieldList);
	
    	
    	return "member/mypage/mypage";
    }
}