package kr.or.ddit.freelancer.free_offer.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.freelancer.free_offer.service.FreeOfferService;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.paging.renderer.DefaultPaginationRenderer;
import kr.or.ddit.paging.renderer.PaginationRenderer;
import kr.or.ddit.position.service.PositionService;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.Free_OfferVO;
import kr.or.ddit.vo.PositionVO;


/**
 * 단건조회 : /freeOffer/{memId}/{projectNo} GET
 * 회원 다건조회 : /freeOffer/{memId} GET
 * 기업 다건조회 : /freeOffer/{companyId} GET
 * 등록 : /freeOffer POST
 * 수정 : /freeOffer/{memId}/{projectNo} PUT
 *
 */
@Controller
@RequestMapping("/freeOffer")
public class FreeOfferController {
	
	public static final String MODELNAME = "freeOffer";
	
	@Inject
	FreeOfferService service;
	@Inject
	PositionService positionService;
	
	@ModelAttribute("codeList")
	public List<CodeVO> codeList() {
		List<CodeVO> codeList = service.selectCodeList();
		return codeList;
	}
	
	@GetMapping("{memId}/{projectNo}")
	public String freeOfferDetail(@PathVariable("memId") String memId
								, @PathVariable("projectNo") String projectNo) {
		
		return "main/freelancer/freeOfferDetail";
	}
	@GetMapping("member/list")
	public String memfreeOfferList(
			@RequestParam(required = false, defaultValue = "1") int page
			, @ModelAttribute("condition") SimpleCondition simpleCondition
			, @AuthenticationPrincipal UserDetails user
			, Model model
	) {
		String memId = user.getUsername();
		PaginationInfo<PositionVO> ppaging = new PaginationInfo<>();
		PaginationInfo<Free_OfferVO> paging = new PaginationInfo<>();
		paging.setCurrentPage(page);
		paging.setSimpleCondition(simpleCondition);
		ppaging.setCurrentPage(page);
		ppaging.setSimpleCondition(simpleCondition);
		
		List<PositionVO> positionList = positionService.selectMemberPositionList(memId, ppaging);
		List<Free_OfferVO> memFreeOfferList = service.memReadFreeOfferList(paging, memId);
		PaginationRenderer renderer = new DefaultPaginationRenderer();
		
		model.addAttribute("pagingHTML", renderer.renderPagination(ppaging, "fnPaging"));
		model.addAttribute("memFreeOfferList", memFreeOfferList);
		model.addAttribute("positionList", positionList);
		return "member/freelancer/freeOfferList";
	}
	@GetMapping("comp/list")
	public String compfreeOfferList(
			@RequestParam(required = false, defaultValue = "1") int page
			, @ModelAttribute("condition") SimpleCondition simpleCondition
			, @AuthenticationPrincipal UserDetails user
			, Model model
	) {
		String companyId = user.getUsername();
		PaginationInfo<Free_OfferVO> paging = new PaginationInfo<>();
		paging.setCurrentPage(page);
		paging.setSimpleCondition(simpleCondition);
		List<Free_OfferVO> compFreeOfferList = service.compReadFreeOfferList(paging, companyId);
		PaginationRenderer renderer = new DefaultPaginationRenderer();
		
		model.addAttribute("pagingHTML", renderer.renderPagination(paging, "fnPaging"));
		model.addAttribute("compFreeOfferList", compFreeOfferList);
		return "company/freelancer/freeOfferList";
	}
	
	@PostMapping
	public ResponseEntity<Map<String, String>> insertFreeOffer(
			  @ModelAttribute(MODELNAME) Free_OfferVO freeOffer
			, BindingResult errors
	) {
		Map<String, String> response = new HashMap<>();
		if (!errors.hasErrors()) {
			ServiceResult result = service.createFreeOffer(freeOffer);
			switch (result) {
			case OK:
				response.put("status", "success");
	            response.put("message", "제안이 성공적으로 완료되었습니다.");
				break;
			case PKDUPLICATED:
				response.put("status", "fail");
	            response.put("message", "이미 제안된 프로젝트입니다.");
				break;
			
			default:
				response.put("status", "error");
	            response.put("message", "실패 하였습니다. 잠시 후 다시 시도해주세요.");
				break;
			}
		} else {
			response.put("status", "error");
            response.put("message", "실패 하였습니다. 잠시 후 다시 시도해주세요.");
		}
		return ResponseEntity.ok(response);  // Map을 JSON으로 반환
	}
	
	@PutMapping("{memId}/{projectNo}")
	public String updateFreeOffer(@PathVariable("memId") String memId
								, @PathVariable("projectNo") String projectNo
								, Free_OfferVO freeOffer) {
		// 회원 수정일 경우 memfreeOfferList 로 이동
		// 기업 수정일 경우 compfreeOfferList 로 이동
		return "company/freelancer/freeOfferList";
	}
	
	@PostMapping("accept")
	@ResponseBody
	public Map<String, Object> acceptOffer(@RequestBody Free_OfferVO freeOffer
			,@AuthenticationPrincipal UserDetails user){
		freeOffer.setMemId(user.getUsername());
		
		ServiceResult result = service.acceptOffer(freeOffer);
		
		Map<String, Object> response = new HashMap<>();
		if (result == ServiceResult.OK) {
			response.put("status", "success");
		} else {
			response.put("status", "error");
		}
		return response;
	}
	
	@PostMapping("reject")
	@ResponseBody
	public Map<String, Object> rejectOffer(@RequestBody Free_OfferVO freeOffer
			,@AuthenticationPrincipal UserDetails user){
		freeOffer.setMemId(user.getUsername());
		
		ServiceResult result = service.rejectOffer(freeOffer);
		
		Map<String, Object> response = new HashMap<>();
		if (result == ServiceResult.OK) {
			response.put("status", "success");
		} else {
			response.put("status", "error");
		}
		return response;
	}
	
}
