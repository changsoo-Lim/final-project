package kr.or.ddit.users.controller;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.json.JsonService;
import kr.or.ddit.users.service.UsersService;
import kr.or.ddit.validate.CompanyCreateGroup;
import kr.or.ddit.validate.CreateGroup;
import kr.or.ddit.validate.MemberCreateGroup;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.StatusVO;
import kr.or.ddit.vo.UsersVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping
public class UsersController {
	
	@Inject
	PasswordEncoder encoder;
	
	@Inject
	UsersService service;
	
	@Inject
	private JsonService jsonService;
	
	public static final String MODELNAME = "user";
		
	@ModelAttribute(MODELNAME)
	public UsersVO user() {
		return new UsersVO();
	}
	
	@GetMapping("login")
	public String loginForm(
			@CookieValue(value = "userId", defaultValue = "") String savedId,
			Authentication authentication,
            Model model
	){
		// 로그인 되어있는 상태에서 로그인 화면으로 접근했을 시 
		if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
	        String userId = ((UserDetails) authentication.getPrincipal()).getUsername();
	        if (!StringUtils.isEmpty(userId)) {
	            return "forward:/index.do";
	        }
	    }
		
		model.addAttribute("savedId", savedId);
		return "main/user/loginForm";
	}
	
	@PostMapping("performLogin")
	public String login(){
		return "/login";
	}
		
	@GetMapping("createUserSuccess")
	public String createUserSuccess() {
		return "main/user/createUserSuccess";
	}
	
	@GetMapping("memberForm")
	public String memberForm(
			Model model
	) {
		model.addAttribute("codeList", service.readCodeGender());
		
		return "main/user/memberForm";
	}
	
	@GetMapping("companyForm")
	public String companyForm(
			Model model
	) {
		List<CodeVO> companySizeCodeList = service.readCompanySizeCode();
		String companyJson = companyJson();
		
		model.addAttribute("companyJson", companyJson);
		model.addAttribute("companySizeCodeList", companySizeCodeList);
		
		return "main/user/companyForm";
	}
	
	@GetMapping("industryCodeList")
	@ResponseBody
	public List<CodeVO> industryCodeList() {
		List<CodeVO> industryCodeList = service.readIndustryCode();
		
		return industryCodeList;
	}
	
	@PostMapping("insertUser/member")
	public String createAccountMember(
			@Validated(value = MemberCreateGroup.class) @ModelAttribute(MODELNAME) UsersVO user
			, BindingResult errors
			, RedirectAttributes redirectAttributes
	) {
		log.info(String.format("★★★★★★★★★★★★★ user : %s ", user.toString()));
		user.setUserPass(encoder.encode(user.getUserPass()));
		StatusVO statusVO = user.getStatusVO();
		if (statusVO.getStatusSms() == null) {
		    statusVO.setStatusSms("N");
		}
		if (statusVO.getStatusEmail() == null) {
		    statusVO.setStatusEmail("N");
		}
		
		redirectAttributes.addFlashAttribute(MODELNAME, user);
		redirectAttributes.addFlashAttribute("errors", errors);
		String lvn = null;
		
		if (!errors.hasErrors()) {
			ServiceResult result = service.createUser(user);
			switch (result) {
			case OK:
				lvn = "redirect:/createUserSuccess";
				break;
			case PKDUPLICATED:
				lvn = "redirect:/memberForm";
				redirectAttributes.addFlashAttribute("message", "아이디 중복, 바꾸셈.");
				break;
			default:
				lvn = "redirect:/memberForm";
				redirectAttributes.addFlashAttribute("message", "서버 오류, 잠시 뒤 다시 가입해보셈.");
				break;
			}
		} else {
			lvn = "redirect:/memberForm";
			String errAttrName = BindingResult.MODEL_KEY_PREFIX + MODELNAME;
			redirectAttributes.addFlashAttribute(errAttrName, errors);
			log.info(String.format("★★★★★★★ errors : ", errors.toString()) );
		}
		return lvn;
	}
	
	@PostMapping("insertUser/company")
	public String createAccountCompany(
			@Validated(value = CompanyCreateGroup.class) @ModelAttribute(MODELNAME) UsersVO user
			, BindingResult errors
			, RedirectAttributes redirectAttributes
	) {
		user.setUserPass(encoder.encode(user.getUserPass()));
		redirectAttributes.addFlashAttribute(MODELNAME, user);
		redirectAttributes.addFlashAttribute("errors", errors);
		String lvn = null;
		
		if (!errors.hasErrors()) {
			ServiceResult result = service.createUser(user);
			switch (result) {
			case OK:
				lvn = "redirect:/createUserSuccess";
				break;
			case PKDUPLICATED:
				lvn = "redirect:/companyForm";
				redirectAttributes.addFlashAttribute("message", "아이디 중복, 바꾸셈.");
				break;
			default:
				lvn = "redirect:/companyForm";
				redirectAttributes.addFlashAttribute("message", "서버 오류, 잠시 뒤 다시 가입해보셈.");
				break;
			}
		} else {
			lvn = "redirect:/companyForm";
			String errAttrName = BindingResult.MODEL_KEY_PREFIX + MODELNAME;
			redirectAttributes.addFlashAttribute(errAttrName, errors);
		}
		return lvn;
	}
	
	@PutMapping("changePass")
	@ResponseBody
	public String changePass(
			  @ModelAttribute(MODELNAME) UsersVO user
			, BindingResult errors
			, RedirectAttributes redirectAttributes
			
	) {
		user.setUserPass(encoder.encode(user.getUserPass()));
		redirectAttributes.addFlashAttribute("errors", errors);
		ServiceResult result = service.modifyPassword(user);
		String updateResult = null;
		if (!errors.hasErrors()) {
			switch (result) {
			case OK:
				updateResult = "true";
				break;
			default:
				updateResult = "false";
				redirectAttributes.addFlashAttribute("message", "서버 오류, 잠시 뒤 다시 가입해보셈.");
				break;
			}
		} else {
			String errAttrName = BindingResult.MODEL_KEY_PREFIX + MODELNAME;
			redirectAttributes.addFlashAttribute(errAttrName, errors);
		}
		return updateResult;
	}
	
	@GetMapping("idCheck/{memId}")
	@ResponseBody
	public UsersVO detail(@PathVariable String memId) {
		return service.readUser(memId);
	}
	
	@GetMapping("deleteUser/companyForm")
	public String deleteCompanyForm() {
		return "index";
	}
	
	@GetMapping("deleteUser/memberForm")
	public String deleteMemberForm() {
		return "index";
	}
	
	@GetMapping("deleteUser/company")
	public String deleteCompany() {
		return "index";
	}
	
	@GetMapping("deleteUser/member")
	public String deleteMember() {
		return "redirect:/index";
	}
	
	private String companyJson() {
		// xml 파일 경로 설정 
		
		
		String filePath = "\\\\192.168.146.56\\stackUp\\resources\\CompanyNum.xml";
		
		//String filePath = "C:\\Users\\JaeHwang\\Desktop\\CompanyNum.xml";
        
        File file = new File(filePath);
        
        if (file.exists()) {
            log.info("파일이 존재합니다: {}", filePath);
        } else {
            log.info("파일이 존재하지 않습니다: {}" , filePath);
        }
        // xml 파일 경로 설정
        
        String json = "";
        
        // json 파싱
        
        // XML -> JSON 변환
        try {
            // JsonService를 통해 XML 데이터를 Map으로 변환
        	 json = jsonService.parseXmlToJson(file.getPath());
        } catch (Exception e) {
            // 예외 발생 시 로그 출력 및 처리
            log.error( e.getMessage());
            e.printStackTrace();
        }
        
        return json;
	}
	
}
