package kr.or.ddit.company.project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.company.project.service.CompanyProjectService;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.paging.renderer.DefaultPaginationRenderer;
import kr.or.ddit.paging.renderer.PaginationRenderer;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.CompanyVO;
import kr.or.ddit.vo.Free_OfferVO;
import kr.or.ddit.vo.ProjectVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("project")
@Slf4j
public class CompanyProjectController {

	@Inject
	private CompanyProjectService service;

	private static final String MODELNAME = "projectVO";

	@ModelAttribute(MODELNAME)
	public ProjectVO proVO() {
		return new ProjectVO();
	}
	
	//@Scheduled(cron = "0 0 0 * * ?") 매일 자정에 실행하고 싶으면 스케쥴러 이용
	@PostConstruct
	public void postConstructupdate() {
		service.constructUpdate();
	}

	@GetMapping
	public String list(
			@AuthenticationPrincipal UserDetails user, Model model
			, @ModelAttribute("condition") SimpleCondition simpleCondition
			,@RequestParam(required = false, defaultValue = "1") int page 
			) {
		
		PaginationInfo<ProjectVO> pagination = new PaginationInfo<ProjectVO>();
		
		pagination.setCurrentPage(page);
		
		List<ProjectVO> proList = service.selectProjectListPaging(user.getUsername(), pagination);

		PaginationRenderer renderer = new DefaultPaginationRenderer();
		String pagingHTML = renderer.renderPagination(pagination,null);
			
		model.addAttribute("pagingHTML", pagingHTML);
		model.addAttribute("proList", proList);

		return "company/project/projectList";
	}

	@GetMapping("form")
	public String form(Model model) {
		
		List<CodeVO> codeList = service.getCode();

		model.addAttribute("codeList", codeList);

		return "company/project/projectForm";
	}

	@GetMapping("{proId}/detail")
	public String detail(@PathVariable("proId") String projectId, Model model) {

		ProjectVO proVO = service.selectCompnyProjectDetail(projectId);
		List<CodeVO> codeList = service.detailCode();

		model.addAttribute(MODELNAME, proVO);
		model.addAttribute("codeList", codeList);

		return "company/project/projectDetail";
	}
	
	
	@GetMapping("{proId}/member")
	public String memberDetail(@PathVariable("proId") String projectId, Model model
			,@AuthenticationPrincipal UserDetails user) {

		ProjectVO proVO = service.selectProjectDetail(projectId);
		CompanyVO compVO = service.selectCompay(proVO.getCompId());
		Free_OfferVO freeOffer = service.selectFreeOffe(user.getUsername(), projectId);
		
		model.addAttribute(MODELNAME, proVO);
		model.addAttribute("company", compVO);
		model.addAttribute("freeOffer", freeOffer);
		return "member/freelancer/memberProjectDetail";
	}
	

	@PostMapping("insert")
	public String insertProject(@Validated(InsertGroup.class) @ModelAttribute(MODELNAME) ProjectVO proVO,
			BindingResult errors, @AuthenticationPrincipal UserDetails user, Model model,
			RedirectAttributes redirectAttributes) {

		proVO.setCompId(user.getUsername());
		log.info("porVO : {}", proVO);

		/*
		 * ,@RequestParam List<String> skills ,@RequestParam(required = false, name =
		 * "region") String region ,@RequestParam List<String> jobs)
		 * 
		 * proVO.setProjectJob(String.join(",", jobs));
		 * proVO.setProjectSkills(String.join(",", skills)); if(region != null &&
		 * !region.isEmpty()) { proVO.setProjectRegion(region); }
		 */
		log.info("errors : {}", errors);
		String lvn = "";
		if (!errors.hasErrors()) {
			ServiceResult result = service.insertProject(proVO);
			if (result == ServiceResult.OK) {
				lvn = "redirect:/project";
			} else {
				lvn = "redirect:/project/form";
				redirectAttributes.addFlashAttribute("message", "서버 오류, 잠시 뒤 다시 시도해주세요.");
			}
		} else {
			redirectAttributes.addFlashAttribute(MODELNAME, proVO);
			redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + MODELNAME, errors);
			lvn = "redirect:/project/form";
		}
		return lvn;
	}

	@GetMapping("{proId}/form")
	public String updateForm(@PathVariable("proId") String projectId, Model model) {

		ProjectVO proVO = service.selectProjectOne(projectId);
		List<CodeVO> codeList = service.getCode();
		model.addAttribute(MODELNAME, proVO);
		model.addAttribute("codeList", codeList);
		return "company/project/projectForm";
	}

	@PostMapping("{proId}/update")
	public String update(@PathVariable("proId") int projectId, @ModelAttribute(MODELNAME) ProjectVO project,
			BindingResult errors, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails user) {

		String lvn = "";
		project.setCompId(user.getUsername());
		project.setProjectNo(projectId);
		log.info(project.toString());
		if (!errors.hasErrors()) {
			ServiceResult result = service.updateProject(project);
			if (result == ServiceResult.OK) {
				lvn = "redirect:/project";
			} else {
				lvn = "redirect:/project/form";
				redirectAttributes.addFlashAttribute("message", "서버 오류, 잠시 뒤 다시 시도해주세요.");
			}
		} else {
			redirectAttributes.addFlashAttribute(MODELNAME, project);
			redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + MODELNAME, errors);
			lvn = "redirect:/project/form";
		}
		return lvn;
	}

	@PutMapping("{proId}/delete")
	@ResponseBody
	public Map<String, Object> delete(@PathVariable("proId") String projectId,
			@AuthenticationPrincipal UserDetails user) {

		Map<String, Object> response = new HashMap<>();
		ServiceResult result = service.deleteProject(projectId);
		log.info("result : {}", result);
		if (result == ServiceResult.OK) {
			response.put("status", "success");
			response.put("message", "프로젝트가 성공적으로 삭제되었습니다.");
		} else {
			response.put("status", "error");
			response.put("message", "프로젝트 삭제 중 문제가 발생했습니다.");
		}
		return response;
	}
	
	@PutMapping("{projectNo}/deletemember")
	@ResponseBody
	public Map<String, Object> deleteMember(@RequestBody List<String> idList
			,@PathVariable("projectNo") String projectNo){
		log.info(idList.toString());
		
		ServiceResult result = service.deleteMember(projectNo, idList);
		Map<String, Object> response = new HashMap<>();
		
		if (result == ServiceResult.OK) {
			response.put("status", "success");
			response.put("message", "성공적으로 제안이 취소 되었습니다.");
		} else {
			response.put("status", "error");
			response.put("message", "제안을 취소 하던중 문제가 발생했습니다.");
		}
		return response;
	}
}