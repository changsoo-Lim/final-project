package kr.or.ddit.position.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.paging.renderer.DefaultPaginationRenderer;
import kr.or.ddit.paging.renderer.PaginationRenderer;
import kr.or.ddit.position.service.PositionService;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.vo.Inter_CompVO;
import kr.or.ddit.vo.PositionVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("position")
@Slf4j
public class PositionController {
	
	@Inject
	private PositionService service;
	
	@GetMapping
	public String list(@AuthenticationPrincipal UserDetails user
			, Model model
			, @ModelAttribute("condition") SimpleCondition simpleCondition
			,@RequestParam(required = false, defaultValue = "1") int page ) {
		
		PaginationInfo<PositionVO> pagination = new PaginationInfo<PositionVO>();
		
		pagination.setCurrentPage(page);
		
		List<PositionVO> positionList = service.selectPositionList(user.getUsername(), pagination);
		
		PaginationRenderer renderer = new DefaultPaginationRenderer();
		String pagingHTML = renderer.renderPagination(pagination,null);
			
		
		model.addAttribute("pagingHTML", pagingHTML);
		model.addAttribute("positionList", positionList);
		
		return "company/position/positionList";
	}
	
	@GetMapping("{positionno}")
	public String detail() {
		
		return "company/position/positionDetail";
	}
	
	
	@PostMapping("insert")
	@ResponseBody
	public Map<String ,Object> insertPosition(@Validated(value = InsertGroup.class) @RequestBody PositionVO position
			, BindingResult errors
			,@AuthenticationPrincipal UserDetails user
			){
		
		position.setCompId(user.getUsername());
		Map<String, Object> response = new HashMap<>();
		if(service.selectPoistionCheck(position) == ServiceResult.OK) {
			System.out.println("값 중복");
			response.put("status", "error");
		}else {
			ServiceResult result = service.insertPosition(position);
			if (result == ServiceResult.OK) {
				response.put("status", "success");
			} else {
				response.put("status", "error");
			}
		}
		return response;
	}
}
