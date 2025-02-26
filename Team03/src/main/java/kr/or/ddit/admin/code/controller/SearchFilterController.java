package kr.or.ddit.admin.code.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.admin.code.service.SearchFilterService;
import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.vo.CodeVO;



@Controller
@RequestMapping("/admin/filter")
public class SearchFilterController {
	
	public static final String MODELNAME = "filter";
	
	@Inject
	private SearchFilterService service;
	
	@ModelAttribute(MODELNAME)
	public CodeVO code() {
		return new CodeVO();
		
	}
	
	// 공통 코드 목록 조회
	@GetMapping
	public String codeList(Model model) throws JsonProcessingException {
		List<CodeVO> codeList = service.readCodeList();
		
		// Convert codeList to JSON string
        ObjectMapper mapper = new ObjectMapper();
        String codeListJson = mapper.writeValueAsString(codeList);
		
	    model.addAttribute("codeListJson", codeListJson);

	    return "admin/filter/searchFilter";
	}
	
	// 공통 코드 등록처리
    @PostMapping("/add")
    public String codeInsert(
    	@ModelAttribute(MODELNAME) CodeVO code
    	, Model model
    	, RedirectAttributes redirectAttributes
    	, BindingResult errors
    ) {
    	redirectAttributes.addFlashAttribute(MODELNAME, code);
    		String lvn = null;
    		if(!errors.hasErrors()) {
    			ServiceResult result = service.createCode(code);
    			switch(result) {
				case OK: 
					lvn = "redirect:/admin/filter";
					break;
				default:
					lvn ="filter/searchFilter";
					redirectAttributes.addFlashAttribute("message","서버오류, 잠시 뒤 다시시도.");
					break;
    			}
    		}else {
    			String errAttrName = BindingResult.MODEL_KEY_PREFIX + MODELNAME;
				redirectAttributes.addFlashAttribute(errAttrName,errors);
				lvn = "redirect:/admin/filter";
    		}
    			return lvn;
    }
    
    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<String> codeUpdate(@RequestBody CodeVO code) {
        ServiceResult result = service.modifyCode(code);
        if (result == ServiceResult.OK) {
            return ResponseEntity.ok("수정 성공");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("수정 실패");
        }
    }
   
    // 코드 삭제 처리
    @PostMapping("/delete/{codeCd}")
    public String codeDelete(
        @PathVariable String codeCd,
        RedirectAttributes redirectAttributes
    ) {
        ServiceResult result = service.removeCode(codeCd);
        switch (result) {
            case OK:
                redirectAttributes.addFlashAttribute("message", "코드가 성공적으로 삭제되었습니다.");
                break;
            default:
                redirectAttributes.addFlashAttribute("message", "삭제에 실패했습니다. 다시 시도해주세요.");
                break;
        }
        return "redirect:/admin/filter";
    }
}
