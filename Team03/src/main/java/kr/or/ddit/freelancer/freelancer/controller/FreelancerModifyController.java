package kr.or.ddit.freelancer.freelancer.controller;

import java.util.List;

import org.aspectj.apache.bcel.generic.LocalVariableGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.freelancer.freelancer.service.FreelancerService;
import kr.or.ddit.member.info.service.MemberInfoService;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.FreelancerVO;
import kr.or.ddit.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;

/**
 * 회원 프리랜서 메인 : /freelancer/freelancerMain GET
 * 등록폼 이동 : /freelancer/freelancerForm GET
 * 수정폼 이동 : /freelancer/freelancerEdit GET
 * 단건조회 : /freelancer/{memId} GET
 * 다건조회 : /freelancer GET
 * 등록 : /freelancer POST
 * 수정 : /freelancer{memId} POST
 * 삭제 : /freelancer{memId} DELETE
 */
@Slf4j
@Controller
@RequestMapping("/freelancer/edit")
public class FreelancerModifyController {
	public static final String MODELNAME = "freelancer";
	
	@Autowired
	private FreelancerService service;
	@Autowired
	private MemberInfoService memInfoService;
	
	@ModelAttribute(MODELNAME)
	public FreelancerVO freelancer() {
		return new FreelancerVO();
	}
	
	@ModelAttribute("codeList")
	public List<CodeVO> codeList() {
		List<CodeVO> codeList = service.selectCodeList();
		return codeList;
	}
	
	@GetMapping
	public String form(@AuthenticationPrincipal UserDetails user, Model model) {
		
		boolean isMember = false;
		if(user != null) {
			isMember = user.getAuthorities().stream()
					.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE02"));
		}
		if(isMember) {
			String memId = user.getUsername();
			MemberVO member = memInfoService.readMemberInfo(memId);
			if(member.getMemFreelancer().equals("N")) {
				// 프리랜서 여부 'N'일 경우 form
				return "redirect:/freelancer/new";
			}else {
				FreelancerVO freelancer = service.readFreelancer(memId);
				freelancer.setFreeDetail(freelancer.getFreeDetail().replace("<br>", "\r\n"));
				model.addAttribute("freelancer", freelancer);
				return "member/freelancer/freelancerEdit";
			}
		}
		
		return "member/freelancer/freelancerEdit";
	}
	
	@PostMapping("{memId}")
	public String updateFreelancer(@PathVariable("memId") String memId
			, @ModelAttribute(MODELNAME) FreelancerVO freelancer
			, BindingResult errors
			, RedirectAttributes redirectAttributes) {
		
		String lvn = null;
		if (!errors.hasErrors()) {
//			4. 통과
//				1) 로직 실행(createMember)
				freelancer.setFreeDetail(freelancer.getFreeDetail().replace("\r\n", "<br>"));
				ServiceResult result = service.modifyFreelancer(freelancer);
				switch (result) {
				case OK:
					// 성공시 프리랜서 디테일
					lvn = "redirect:/freelancer/edit";
					break;
				default:
					lvn = "redirect:/freelancer/edit";
					redirectAttributes.addFlashAttribute("message", "잠시 후에 다시 이용하여 주십시오.");
					break;
				}
			} else {
				// 실패시 수정폼
				String errAttrName = BindingResult.MODEL_KEY_PREFIX+MODELNAME;
				redirectAttributes.addFlashAttribute(errAttrName, errors);
				lvn = "redirect:/freelancer/edit";
			}
		freelancer.setAtchFile(null);
		return lvn;
	}
	
	@DeleteMapping("{memId}")
	public String delete(@PathVariable String memId, RedirectAttributes redirectAttributes) {
		service.removeFreelancer(memId);
		return "redirect:/freelancer/main";
	}
}
