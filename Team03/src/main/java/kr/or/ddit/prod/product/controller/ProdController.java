package kr.or.ddit.prod.product.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import kr.or.ddit.prod.product.service.ProdService;
import kr.or.ddit.vo.ProductVO;

@Controller
@RequestMapping("/admin/products")
public class ProdController {
	
	@Inject
	ProdService service;
	
	public static final String MODELNAME = "prod";
	
	@ModelAttribute(MODELNAME)
	public ProductVO prod() {
		return new ProductVO();
	}
	
	@GetMapping("/ad")
	public String form(Model model) {
		List<ProductVO> prodList = service.readProdList();
		model.addAttribute("prodList", prodList);
		return "admin/prod/prodForm";
	}
	@PostMapping("/ad")
	public String formPost(
	        @Valid @ModelAttribute(MODELNAME) ProductVO prod,
	        BindingResult errors,
	        RedirectAttributes redirectAttributes
	) {
	    String lvn = null;
	    ServiceResult result = service.createtProd(prod);
	    
	    if (!errors.hasErrors()) {
	        switch (result) {
	            case OK:
	                lvn = "redirect:/admin/products/ad";
	                break;
	            default:
	                redirectAttributes.addFlashAttribute(MODELNAME, prod);
	                redirectAttributes.addFlashAttribute("message", "서버 오류, 잠시 후 다시 시도하세요.");
	                lvn = "redirect:/admin/products/ad";
	                break;
	        }
	    } else {
	        String errAttrName = BindingResult.MODEL_KEY_PREFIX + MODELNAME;
	        redirectAttributes.addFlashAttribute(errAttrName, errors);
	        redirectAttributes.addFlashAttribute(MODELNAME, prod);
	        lvn = "redirect:/admin/products/ad";
	    }
	    
	    return lvn;
	}

	
	@GetMapping("{productCd}/detail")
	@ResponseBody
	public ProductVO detail(@PathVariable String productCd) {
		return service.readProd(productCd);
	}
	
	@PutMapping("{productCd}/edit")
	@ResponseBody
	public Map<String, Object> editPost(
		@ModelAttribute ProductVO prod,
	    BindingResult errors
	) {
		Map<String, Object> response = new HashMap<>();
	    ServiceResult result = service.modifiyProd(prod);

	    if (!errors.hasErrors()) {
	        switch (result) {
	        case OK:
	            response.put("status", "success");
	            response.put("message", "수정 성공");
	            break;

	        default:
	            response.put("status", "error");
	            response.put("message", "서버오류, 잠시 뒤 다시시도.");
	            break;
	        }
	    }
	    return response;
	}

	
	@DeleteMapping("{productCd}/delete")
	@ResponseBody
	public ResponseEntity<String> del(@PathVariable String productCd) {
		ServiceResult result = service.removeProd(productCd);
		switch (result) {
		case OK:
			return ResponseEntity.ok("삭제 성공");
		default:
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 실패");
		}
	}
}
