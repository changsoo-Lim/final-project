package kr.or.ddit.prod.buy.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.prod.buy.service.BuyService;
import kr.or.ddit.prod.product.dao.ProdMapper;
import kr.or.ddit.vo.BuyVO;
import kr.or.ddit.vo.EmployVO;
import kr.or.ddit.vo.ProductVO;

@Controller
@RequestMapping("/buy")
public class BuyController {
	
	public static final String MODELNAME = "buy";
	
	@Inject
	ProdMapper prodMapper;
	
	@Inject
	BuyService service;
	
	@ModelAttribute(MODELNAME)
	public BuyVO buy() {
		return new BuyVO();
	}
	
	@ModelAttribute("prodList")
	public List<ProductVO> prodList(){
		
		return prodMapper.selectProdList();
	}
	
	
	@GetMapping // 상품 구매 창
	public String view(Authentication authentication,Model model) {
		String compId = ((UserDetails) authentication.getPrincipal()).getUsername();
		List<EmployVO> empList = service.empList(compId);
		
		model.addAttribute("empList", empList);
		
		return "company/buy/buyView";
	}
	
	@PostMapping // 상품 구매
	public String formPost(
			Authentication authentication,
			@ModelAttribute(MODELNAME) BuyVO buy
			, BindingResult errors
			, RedirectAttributes redirectAttributes
	) {
		if(authentication==null) {
			return "redirect:/login";
		}
		String compId = ((UserDetails) authentication.getPrincipal()).getUsername();
		String lvn = null;
		buy.setCompId(compId);
		ServiceResult result = service.createBuy(buy);
		
		if (!errors.hasErrors()) {
	        switch (result) {
	            case OK:
	                lvn = "redirect:/buy/"+buy.getCompId()+"/list";
	                break;
	            default:
	                redirectAttributes.addFlashAttribute(MODELNAME, buy);
	                redirectAttributes.addFlashAttribute("message", "서버 오류, 잠시 후 다시 시도하세요.");
	                lvn = "redirect:/buy";
	                break;
	        }
	    } else {
	        String errAttrName = BindingResult.MODEL_KEY_PREFIX + MODELNAME;
	        redirectAttributes.addFlashAttribute(errAttrName, errors);
	        redirectAttributes.addFlashAttribute(MODELNAME, buy);
	        lvn = "redirect:/buy";
	    }
	    
	    return lvn;
	}
	

	@GetMapping("{compId}/list") // 나의 구매 리스트
	public String readList(
			@PathVariable String compId
			, Model model
	) {
		List<BuyVO> buyList = service.readBuyList(compId);
		
		model.addAttribute("buyList", buyList);
		
		return "company/buy/buyList";
	}
	
	@GetMapping("{compId}/history") // 나의 구매 상세보기
	public String read() {
		return "company/buy/buyDetail";
	}
	
	
}
