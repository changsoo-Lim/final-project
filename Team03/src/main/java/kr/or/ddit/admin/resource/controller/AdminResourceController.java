package kr.or.ddit.admin.resource.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.admin.resource.service.AdminResourceService;
import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.file.service.FileService;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.paging.renderer.DefaultPaginationRenderer;
import kr.or.ddit.paging.renderer.PaginationRenderer;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.ResourceVO;

@Controller
@RequestMapping("admin/resource")
public class AdminResourceController {
	public static final String MODELNAME = "resource";
	
	@Inject
	private AdminResourceService service;
	
	@Inject
	private FileService fileService;
	
	@ModelAttribute(MODELNAME)
	public ResourceVO resource() {
		return new ResourceVO();
	}
	
	// 관리자 자료실 자료 리스트
	@GetMapping
	public String list(
		Optional<Integer> page
		,@ModelAttribute("condition") SimpleCondition simpleCondition
		, Model model
	) {
		PaginationInfo paging = new PaginationInfo();
		paging.setCurrentPage(page.orElse(1));
		paging.setSimpleCondition(simpleCondition);
		
		// 여기만 12개 목록으로 출력
		String targetJSP = "admin/resource/adminResourceList";
	    if ("admin/resource/adminResourceList".equals(targetJSP)) { 
	        paging.setScreenSize(12); 
	    } else {
	        paging.setScreenSize(10); 
	    }
		List<ResourceVO> adminResourceList = service.readAdminResourceList(paging);
		
		// 총 게시글 수 가져오기
	    int totalCount = service.getTotalCount(simpleCondition);
		
		PaginationRenderer renderer = new DefaultPaginationRenderer();
		String pagingHTML = renderer.renderPagination(paging, null);
		
		model.addAttribute("adminResourceList", adminResourceList);
		model.addAttribute("pagingHTML", pagingHTML);
		model.addAttribute("totalCount", totalCount); 
		
		return "admin/resource/adminResourceList";
	}
	
	// 관리자 자료실 자료 상세조회
	@GetMapping("{resourceNo}")
	public String detail(@PathVariable int resourceNo, Model model) {
		ResourceVO resource = service.readAdminResource(resourceNo);
		model.addAttribute("resource",resource);
		
		return "admin/resource/adminResourceDetail";
	}
	
	// 관리자 자료 등록 폼
	@GetMapping("/adminResourceForm")
	public String insertform() {
		return "admin/resource/adminResourceForm";
	}
	
	// 자료 등록 처리
	@PostMapping("/adminResourceForm")
	public String insert(
		@ModelAttribute(MODELNAME) ResourceVO resource
		,BindingResult errors
		, RedirectAttributes redirectAttributes
	)throws IOException {
		redirectAttributes.addFlashAttribute(MODELNAME,resource);
		String lvn = null;
		if(!errors.hasErrors()) {
			ServiceResult result = service.createAdminResource(resource);
			switch (result) {
			case OK:
				lvn = "redirect:/admin/resource";
				break;
			default:
				lvn = "resource/adminResourceForm";
				redirectAttributes.addFlashAttribute("message","서버오류,잠시 뒤 다시시도");
				break;
			}
		}else {
			String errAttrName = BindingResult.MODEL_KEY_PREFIX + MODELNAME;
			redirectAttributes.addFlashAttribute(errAttrName,errors);
			lvn = "redirect:/admin/resource";
		}
		return lvn;
	}
	
	// 관리자 자료실 자료 수정 폼
	@GetMapping("{resourceNo}/adminResourceEdit")
	public String updateForm(
		@PathVariable("resourceNo") int resourceNo
		, Model model
	) {
		ResourceVO resource = service.readAdminResource(resourceNo);
		model.addAttribute(MODELNAME,resource);
		
		return "admin/resource/adminResourceEdit";
	}
	
	// 관리자 자료실 자료 수정 처리
	@PostMapping("{resourceNo}/adminResourceEdit")
	public String update(
		@ModelAttribute(MODELNAME) ResourceVO resource
		, BindingResult errors
		, RedirectAttributes redirectAttributes
	) {
		String lvn = null;
		if(!errors.hasErrors()) {
			ServiceResult result = service.modifyAdminResource(resource);
			switch (result) {
			case OK:
				lvn = "redirect:/admin/resource/"+resource.getResourceNo();
				break;
				
			default:
				lvn = "redirect:/admin/resource";
				redirectAttributes.addFlashAttribute("message","서버오류");
				break;
			}
		}else {
			String errAttrName = BindingResult.MODEL_KEY_PREFIX + MODELNAME;
	         redirectAttributes.addFlashAttribute(errAttrName, errors);
	         lvn = "redirect:/admin/resource";
		}
		resource.setFile(null);
		return lvn;
	}
	
	
	
	// 관리자 자료 삭제
	@PostMapping("{resourceNo}")
	public String delete(
		@PathVariable int resourceNo
		, RedirectAttributes redirectAttributes
	) {
		String lvn = null;
		ServiceResult result = service.removeAdminResource(resourceNo);
		switch (result) {
		case OK:
			lvn = "redirect:/admin/resource";
			break;
		default:
			redirectAttributes.addFlashAttribute("message", "삭제 실패 !");
			lvn = "redirect:/admin/resource";
			break;
		}
		return lvn;
	}
	
	
	
}
