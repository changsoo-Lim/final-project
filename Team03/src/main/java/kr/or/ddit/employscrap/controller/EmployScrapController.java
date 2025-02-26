package kr.or.ddit.employscrap.controller;

import java.util.HashMap;
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

import kr.or.ddit.employscrap.service.EmployScrapService;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.paging.renderer.DefaultPaginationRenderer;
import kr.or.ddit.paging.renderer.PaginationRenderer;
import kr.or.ddit.vo.EmployscrapVO;

@Controller
@RequestMapping("/employScrap")
public class EmployScrapController {
	
	public static final String MODELNAME = "empScrap";
	
	@Inject
	private EmployScrapService service;
	
	// 관심공고 목록
	@GetMapping
	public String readEmpScrapList(@RequestParam(required = false, defaultValue = "1") int page
			, @ModelAttribute("condition") SimpleCondition simpleCondition
			, @AuthenticationPrincipal UserDetails user, Model model
	) {
		PaginationInfo<EmployscrapVO> paging = new PaginationInfo<>();
		paging.setCurrentPage(page);
	    paging.setSimpleCondition(simpleCondition);
	    PaginationRenderer renderer = new DefaultPaginationRenderer();
	    model.addAttribute(MODELNAME, service.readEmpScrap(user.getUsername(), paging));
		model.addAttribute("pagingHTML", renderer.renderPagination(paging, "fnPaging"));
		return "member/scrap/memScrap";
	}
	
	// 관심공고 추가/삭제
	@PostMapping
	public ResponseEntity<Map<String, String>> createEmpScrap(
			@RequestBody EmployscrapVO employScrap
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
				employScrap.setMemId(user.getUsername());
				if(service.updateScrapStatus(employScrap) > 0) {
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
}
