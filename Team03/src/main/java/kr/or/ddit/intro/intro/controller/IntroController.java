package kr.or.ddit.intro.intro.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.intro.intro.IntroDTO;
import kr.or.ddit.intro.intro.service.IntroService;
import kr.or.ddit.intro.intro_detail.service.Intro_DetailService;
import kr.or.ddit.vo.IntroVO;
import kr.or.ddit.vo.Intro_DetailVO;

@Controller
@RequestMapping("/intro")
public class IntroController {
	

	@Autowired
	private IntroService service;
	
	@Inject
	private Intro_DetailService detailService;

	@ModelAttribute("intro")
	public IntroVO intro() {
		return new IntroVO();
	}
	@ModelAttribute("introDetail")
	public Intro_DetailVO introDetail() {
		return new Intro_DetailVO();
	}
	
	
	@GetMapping
	public String list(
		Authentication authentication
		,Model model
	) {
		if(authentication==null) {
			return "redirect:/login";
		}
		String memId = ((UserDetails) authentication.getPrincipal()).getUsername();
		List<IntroVO> introList = service.readListIntro(memId);
		model.addAttribute("intro", introList);
		return "member/intro/introList";
	}
	
	@GetMapping("new")
	public String form(Authentication authentication) {
		
		return "member/intro/introForm";
	}
	
	@PostMapping("/new")
	@ResponseBody
	public Map<String, Object> insert(@RequestBody IntroDTO introDTO,Authentication authentication) {
	    Map<String, Object> response = new HashMap<>();
	    String memId = ((UserDetails) authentication.getPrincipal()).getUsername();
	    try {
	        // 부모 테이블 저장
	        IntroVO intro = new IntroVO();
	        intro.setIntroTitle(introDTO.getIntroTitle());
	        intro.setMemId(memId);
	        ServiceResult introResult = service.createIntro(intro);

	        if (introResult == ServiceResult.OK) {
	        	// 자식 테이블 저장
		        for (Intro_DetailVO detail : introDTO.getIntroDetails()) {
		            detail.setIntroNo(intro.getIntroNo());
		            if (detailService.createIntroDetail(detail) != ServiceResult.OK) {
		                response.put("success", false);
		                response.put("message", "세부 데이터 저장 실패");
		                return response;
		            }
		        }
	        }

	        response.put("success", true);
	    } catch (Exception e) {
	        response.put("success", false);
	        response.put("message", "서버 오류 발생: " + e.getMessage());
	    }
	    return response;
	}




	
	@DeleteMapping("{introNo}")
	@ResponseBody
	public String del(@PathVariable String introNo,RedirectAttributes redirectAttributes) {
		
		String lvn = null;
		ServiceResult result = service.removeIntro(introNo);
		switch (result) {
			case OK:
				lvn = "redirect:/intro";
				break;
				
			default:
				redirectAttributes.addFlashAttribute("message", "삭제 실패 !");
				lvn = "redirect:/intro";
				break;
		}
		return lvn;
	}
	
	@GetMapping("{introNo}/edit") // 하위 자기소개서 폼으로 이동
	public String edit(
	    @PathVariable("introNo") int introNo,
	    Model model
	) {
	    IntroVO intro = service.readIntro(introNo);
	    List<Intro_DetailVO> introDetailList = detailService.readListIntroDetail(introNo);
	
	    model.addAttribute("intro", intro);
	    model.addAttribute("introDetail", introDetailList);
	    return "member/intro/introEdit"; // 뷰 이름 반환
	}

	
	@PutMapping("{introNo}/edit") // 하위 자기소개서 수정
	@ResponseBody
	public Map<String, Object> postEdit(
			@RequestBody IntroDTO introDTO,
			@PathVariable("introNo") int introNo,
			Authentication authentication
	) {
	    Map<String, Object> response = new HashMap<>();
	    String memId = ((UserDetails) authentication.getPrincipal()).getUsername();
	    try {
	        // 부모 데이터 저장
	        IntroVO intro = new IntroVO();
	        intro.setIntroNo(introNo);
	        intro.setIntroTitle(introDTO.getIntroTitle());
	        intro.setMemId(memId);
	        ServiceResult introResult = service.modifyIntro(intro);

	        if (introResult == ServiceResult.OK) {
	        	// 자식 데이터 저장
		        for (Intro_DetailVO detail : introDTO.getIntroDetails()) {
		            detail.setIntroNo(intro.getIntroNo());
		            if (detailService.modifyIntroDetail(detail) != ServiceResult.OK) {
		                response.put("success", false);
		                response.put("message", "세부 데이터 저장 실패");
		                return response;
		            }
		        }
	        }

	        response.put("success", true);
	    } catch (Exception e) {
	        response.put("success", false);
	        response.put("message", "서버 오류 발생: " + e.getMessage());
	    }
	    return response;
	}
	
	@PostMapping("{introNo}/editNew")
	@ResponseBody
	public Map<String, Object> editNew(@RequestBody IntroDTO introDTO,@PathVariable("introNo") int introNo) {
	    Map<String, Object> response = new HashMap<>();
	    try {
	    	IntroVO result = service.readIntro(introNo);
	        if (result != null) {
	        	// 자식 테이블 저장
		        for (Intro_DetailVO detail : introDTO.getIntroDetails()) {
		            detail.setIntroNo(result.getIntroNo());
		            if (detailService.createIntroDetail(detail) != ServiceResult.OK) {
		                response.put("success", false);
		                response.put("message", "세부 데이터 저장 실패");
		                return response;
		            }
		        }
	        }else {
	        	response.put("success", false);
	            response.put("message","해당 introNo에 대한 데이터를 찾을 수 없습니다.");
	            return response;
	        }

	        response.put("success", true);
	    } catch (Exception e) {
	        response.put("success", false);
	        response.put("message", "서버 오류 발생: " + e.getMessage());
	    }
	    return response;
	}
}
