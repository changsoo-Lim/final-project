package kr.or.ddit.blacklist.controller;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.blacklist.service.BlackListService;
import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.paging.renderer.DefaultPaginationRenderer;
import kr.or.ddit.paging.renderer.PaginationRenderer;
import kr.or.ddit.vo.BlacklistVO;
import kr.or.ddit.vo.BoardVO;

@Controller
@RequestMapping("/admin/blacklist")
public class BlackListController {
	public static final String MODELNAME = "black";
	
	@Inject
	private BlackListService service;
	
	@ModelAttribute(MODELNAME)
	public BlacklistVO black() {
		return new BlacklistVO();
		
	}
	
	// 블랙리스트 목록 조회
	@GetMapping
	public String blacklistList(
			Optional<Integer> page ,
			@ModelAttribute("condition") SimpleCondition simpleCondition,
			Model model
	) {
		PaginationInfo paging = new PaginationInfo();
		paging.setCurrentPage(page.orElse(1));
		paging.setSimpleCondition(simpleCondition);
		
		List<BlacklistVO> BlacklistList = service.readBlacklistList(paging);
		
		int totalCount = service.getTotalCount(paging);
		
		PaginationRenderer renderer = new DefaultPaginationRenderer();
		String pagingHTML = renderer.renderPagination(paging, null);
		
		model.addAttribute("BlacklistList", BlacklistList);
		model.addAttribute("pagingHTML", pagingHTML);
		model.addAttribute("totalCount", totalCount); 

		return "admin/blacklist/blacklistList";
	}
	
	
	// 블랙리스트 해체(화이트리스트 화)
	@PostMapping("{blacklistNo}")
	public String blacklistDelete(
			@PathVariable int blacklistNo
			, RedirectAttributes redirectAttributes
		) {
			String lvn = null;
			ServiceResult result = service.removeBlacklist(blacklistNo);
			switch (result) {
				case OK:
					lvn = "redirect:/admin/blacklist";
					break;
					
				default:
					redirectAttributes.addFlashAttribute("message", "삭제 실패 !");
					lvn = "redirect:/admin/blacklist";
					break;
			}
			return lvn;
		}
	// 블랙리스트 다중해제(화이트리스트 화)
	@PostMapping("deleteMultiple")
	public String deleteMultiple(
		@RequestParam("blacklistNo") List<Integer> blacklistNo
		, RedirectAttributes redirectAttributes
	) {
		if (blacklistNo == null || blacklistNo.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "삭제할 게시글을 선택하세요.");
            return "redirect:/admin/blacklist";
        }
        ServiceResult result = service.removeBlacklists(blacklistNo);
        if (result == ServiceResult.OK) {
            redirectAttributes.addFlashAttribute("message", "블랙리스트 해제되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("message", "블랙리스트 해제 실패.");
        }
        return "redirect:/admin/blacklist";
	}
}
