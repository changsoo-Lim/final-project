package kr.or.ddit.freelancer.freelancer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.freelancer.freelancer.service.FreelancerService;
import kr.or.ddit.member.info.service.MemberInfoService;
import kr.or.ddit.member.member.service.MemberService;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.FreelancerVO;
import kr.or.ddit.vo.MemberVO;

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

@Controller
@RequestMapping("/freelancer/new")
public class FreelancerInsertController {
	public static final String MODELNAME = "newFreelancer";
	
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
	
	// 프리랜서 등록 폼
	@GetMapping
	public String form(@AuthenticationPrincipal UserDetails user, Model model, RedirectAttributes attributes) {
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
				return "member/freelancer/freelancerForm";
			}else {
				FreelancerVO freelancer = service.readFreelancer(memId);
				if(!freelancer.getFreeDetail().isEmpty()) {
					freelancer.setFreeDetail(freelancer.getFreeDetail().replace("<br>", "\r\n"));
				}
				model.addAttribute("freelancer", freelancer);
				return "redirect:/freelancer/edit";
			}
		}else {
			return "redirect:/login";
		}
	}
	
	@PostMapping
	public String insert(
			@Validated(InsertGroup.class) @ModelAttribute(MODELNAME) FreelancerVO newFreelancer
			, BindingResult errors
			, RedirectAttributes redirectAttributes
			, @AuthenticationPrincipal UserDetails user
	) {
		String lvn = null;
		if (!errors.hasErrors()) {
			newFreelancer.setFreeDetail(newFreelancer.getFreeDetail().replace("\r\n", "<br>"));
			ServiceResult result = service.createFreelancer(newFreelancer);
			switch (result) {
			case OK:
				lvn = "redirect:/freelancer/edit";
				break;

			default:
				lvn = "redirect:/freelancer/new";
				redirectAttributes.addFlashAttribute("message", "서버 오류, 잠시 뒤 다시 시도해주세요.");
				break;
			}
		} else {
			redirectAttributes.addFlashAttribute(MODELNAME, newFreelancer);
			redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + MODELNAME, errors);
			lvn = "redirect:/freelancer/new";
		}
		return lvn;
	}
}
