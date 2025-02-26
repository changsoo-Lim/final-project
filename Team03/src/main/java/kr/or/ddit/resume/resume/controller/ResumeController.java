package kr.or.ddit.resume.resume.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.member.info.service.MemberInfoService;
import kr.or.ddit.resume.resume.ResumeDTO;
import kr.or.ddit.resume.resume.dao.ResumeMapper;
import kr.or.ddit.resume.resume.service.ResumeService;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.MemberVO;

@Controller
@RequestMapping("/resume")
public class ResumeController {
	@Inject
	private ResumeMapper mapper;
	
	@ModelAttribute("graduationList")
	public List<CodeVO> graduationList(){
		return mapper.selectGraduationList();
	}
	@ModelAttribute("highMajorList")
	public List<CodeVO> highMajorList(){
		return mapper.selectHighMajorList();
	}
	@ModelAttribute("admissionList")
	public List<CodeVO> admissionList(){
		return mapper.selectAdmissionList();
	}
	@ModelAttribute("univTypeList")
	public List<CodeVO> univTypeList(){
		return mapper.selectUnivTypeList();
	}
	@ModelAttribute("univMajorList")
	public List<CodeVO> univMajorList(){
		return mapper.selectUnivMajorList();
	}
	@ModelAttribute("univDegreeList")
	public List<CodeVO> univDegreeList(){
		return mapper.selectUnivDegreeList();
	}
	@ModelAttribute("industryList")
	public List<CodeVO> industryList(){
		return mapper.selectIndustryList();
	}
	@ModelAttribute("industryDetailList")
	public List<CodeVO> industryDetailList(){
		return mapper.selectIndustryDetailList();
	}
	@ModelAttribute("companySizeList")
	public List<CodeVO> companySizeList(){
		return mapper.selectCompanySizeList();
	}
	@ModelAttribute("companyTypeList")
	public List<CodeVO> companyTypeList(){
		return mapper.selectCompanyTypeList();
	}
	@ModelAttribute("companyListingList")
	public List<CodeVO> companyListingList(){
		return mapper.selectCompanyListingList();
	}
	@ModelAttribute("cityList")
	public List<CodeVO> cityList(){
		return mapper.selectCityList();
	}
	@ModelAttribute("cityDetailList")
	public List<CodeVO> cityDetailList(){
		return mapper.selectCityDetailList();
	}
	@ModelAttribute("workTypeList")
	public List<CodeVO> workTypeList(){
		return mapper.selectWorkTypeList();
	}
	@ModelAttribute("resignReasonList")
	public List<CodeVO> resignReasonList(){
		return mapper.selectResignReasonList();
	}
	@ModelAttribute("cretList")
	public List<CodeVO> cretList(){
		return mapper.selectCretList();
	}
	@ModelAttribute("comList")
	public List<CodeVO> comList(){
		return mapper.selectComList();
	}
	@ModelAttribute("comDetailList")
	public List<CodeVO> comDetailList(){
		return mapper.selectComDetailList();
	}
	@ModelAttribute("comLVList")
	public List<CodeVO> comLVList(){
		return mapper.selectComLVList();
	}
	@ModelAttribute("langList")
	public List<CodeVO> langList(){
		return mapper.selectLangList();
	}
	@ModelAttribute("speakList")
	public List<CodeVO> speakList(){
		return mapper.selectSpeakList();
	}
	@ModelAttribute("readList")
	public List<CodeVO> readList(){
		return mapper.selectReadList();
	}
	@ModelAttribute("writeList")
	public List<CodeVO> writeList(){
		return mapper.selectWriteList();
	}
	@ModelAttribute("langTestList")
	public List<CodeVO> langTestList(){
		return mapper.selectLangTestList();
	}
	@ModelAttribute("countryList")
	public List<CodeVO> countryList(){
		return mapper.selectCountryList();
	}
	@ModelAttribute("workList")
	public List<CodeVO> workList(){
		return mapper.selectCityList();
	}
	@ModelAttribute("workDetailList")
	public List<CodeVO> workDetailList(){
		return mapper.selectCityDetailList();
	}
	@ModelAttribute("salaryList")
	public List<CodeVO> salaryList(){
		return mapper.selectSalaryList();
	}
	@ModelAttribute("URLList")
	public List<CodeVO> URLList(){
		return mapper.selectURLList();
	}
	@ModelAttribute("certList")
	public List<CodeVO> certList(){
		return mapper.selectCertList();
	}
	
	@Inject
	private ResumeService service;
	
	@Inject
	private MemberInfoService memberService;
	
	@GetMapping("new")
	public String form(Authentication authentication, Model model) {
		
		if(authentication==null) {
			return "redirect:/login";
		}
		String memId = ((UserDetails) authentication.getPrincipal()).getUsername();

		MemberVO member = memberService.readMemberInfo(memId);
		
		model.addAttribute("member", member);
		return "member/resume/resumeForm";
	}
	
	@PostMapping("new")
	@ResponseBody
	public Map<String, Object> formPost(Authentication authentication,@RequestBody ResumeDTO resume) {
	    
		Map<String, Object> response = new HashMap<>();
		
		String memId = ((UserDetails) authentication.getPrincipal()).getUsername();
		
		ServiceResult result = service.insertAndUpdateResume(memId,resume);
	    
	    switch (result) {
	        case OK:
	            response.put("status", "success");
	            response.put("message", "등록 성공");
	            break;

	        case FAIL:
	            response.put("status", "fail");
	            response.put("message", "등록 실패");
	            break;
	            
	        default:
	            response.put("status", "error");
	            response.put("message", "등록 오류");
	            break;
	    }
	    return response;
	}
	
	@PostMapping("image")
	@ResponseBody
	public Map<String, Object> image(Authentication authentication,MemberVO member){
		Map<String, Object> response = new HashMap<>();
		String memId = ((UserDetails) authentication.getPrincipal()).getUsername();
		member.setMemId(memId);
		ServiceResult result = service.updateMemberImage(member);
		
		switch (result) {
		case OK:
			response.put("status", "success");
            response.put("message", "등록 성공");
			break;
			
		case FAIL:
            response.put("status", "fail");
            response.put("message", "등록 실패");
            break;
            
		default:
			response.put("status", "error");
            response.put("message", "등록 오류");
            break;
		}
		
		return response;
	}

}
