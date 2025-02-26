package kr.or.ddit.inter_comp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.inter_comp.service.InterCompService;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.paging.renderer.DefaultPaginationRenderer;
import kr.or.ddit.paging.renderer.PaginationRenderer;
import kr.or.ddit.vo.Inter_CompVO;
import kr.or.ddit.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/interComp")
@Slf4j
public class InterCompController {
	
	public static final String MODELNAME = "interComp";
	
	@Inject
	private InterCompService service;
	
	// 관심공고 목록
	@GetMapping
	public String readEmpScrapList(@RequestParam(required = false, defaultValue = "1") int page
			, @ModelAttribute("condition") SimpleCondition simpleCondition
			, @AuthenticationPrincipal UserDetails user, Model model
	) {
		model.addAttribute(MODELNAME, service.readInterComp(user.getUsername()));
		return "";
	}
	
	// 관심공고 추가/삭제
	@PostMapping
	public ResponseEntity<Map<String, String>> createEmpScrap(
			@RequestBody Inter_CompVO interComp
			, BindingResult errors
			, @AuthenticationPrincipal UserDetails user
	) {
		Map<String, String> response = new HashMap<>();
		boolean isMember = false;
		boolean isComp = false;
		if(user != null) {
			isMember = user.getAuthorities().stream()
					.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE02"));
			
			isComp = user.getAuthorities().stream()
					.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE03"));
			
		}
		if(isMember) {
			if (!errors.hasErrors()) {
				interComp.setMemId(user.getUsername());
				if(service.updateScrapStatus(interComp) > 0) {
					response.put("status", "success");
				}else {
					response.put("status", "fail");
				}
			}else {
				String errAttrName = BindingResult.MODEL_KEY_PREFIX+MODELNAME;
				response.put("status", "error");
				response.put(errAttrName, errors.toString());
				response.put("message", "서버 오류. 잠시 후 다시 시도해주세요.");
			}
		}else if(isComp) {
			response.put("status", "comp");
		}else {
			response.put("status", "anonymous");
		}
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("memberlist")
	public String selectInterMember(@AuthenticationPrincipal UserDetails user,
			Model model
			, @ModelAttribute("condition") SimpleCondition simpleCondition
			,@RequestParam(required = false, defaultValue = "1") int page ){
		
		PaginationInfo<Inter_CompVO> pagination = new PaginationInfo<Inter_CompVO>();
		
		pagination.setCurrentPage(page);
		
		List<Inter_CompVO> memList = service.selectMemList(user.getUsername(), pagination);
		
		PaginationRenderer renderer = new DefaultPaginationRenderer();
		String pagingHTML = renderer.renderPagination(pagination,null);
			
		model.addAttribute("pagingHTML", pagingHTML);
		model.addAttribute("memList", memList);
		
		return "company/interest/interestList";
	}
}
