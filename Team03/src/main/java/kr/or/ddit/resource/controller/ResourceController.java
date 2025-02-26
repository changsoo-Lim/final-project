package kr.or.ddit.resource.controller;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.file.service.FileService;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.paging.renderer.DefaultPaginationRenderer;
import kr.or.ddit.paging.renderer.PaginationRenderer;
import kr.or.ddit.resource.service.ResourceService;
import kr.or.ddit.vo.ResourceVO;

@Controller
@RequestMapping("main/resource")
public class ResourceController {
	
	public static final String MODELNAME = "resource";
	
	@Inject
	private ResourceService service;
	@Inject
	private FileService fileService;
	
	@ModelAttribute(MODELNAME)
	public ResourceVO resource() {
		return new ResourceVO();
	}
	
	// 자료실 자료 리스트 조회
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
		String targetJSP = "main/resource/resourceList";
	    if ("main/resource/resourceList".equals(targetJSP)) { 
	        paging.setScreenSize(12); 
	    } else {
	        paging.setScreenSize(10); 
	    }
		List<ResourceVO> resourceList = service.readResourceList(paging);
		
		// 총 게시글 수 가져오기
	    int totalCount = service.getTotalCount(paging);
		
	    PaginationRenderer renderer = new DefaultPaginationRenderer();
		String pagingHTML = renderer.renderPagination(paging, null);
		
		model.addAttribute("resourceList", resourceList);
		model.addAttribute("pagingHTML", pagingHTML);
		model.addAttribute("totalCount", totalCount);
//		model.addAttribute("currentPage", paging.getCurrentPage());
		
	    
		return "main/resource/resourceList";
	}
	
	// 자료실 자료 상세조회
	@GetMapping("{resourceNo}")
	public String detail(@PathVariable int resourceNo, Model model) {
		ResourceVO resource = service.readResource(resourceNo);
		model.addAttribute("resource",resource);
		
		return "main/resource/resourceDetail";
	}
	
}
