package kr.or.ddit.admin.notice.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.admin.notice.service.AdminHelpService;
import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.paging.renderer.DefaultPaginationRenderer;
import kr.or.ddit.paging.renderer.PaginationRenderer;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.validate.UpdateGroup;
import kr.or.ddit.vo.NoticeVO;

@Controller
@RequestMapping("/admin/faq")
public class AdminFaqController {
	public static final String MODELNAME = "faq";

    @Autowired
    private AdminHelpService service;

    @ModelAttribute(MODELNAME)
    public NoticeVO faq() {
        return new NoticeVO();
    }
    
    private String formatTimeDifference(LocalDateTime noticeDateTime) {
	    LocalDateTime now = LocalDateTime.now();
	    long minutesAgo = ChronoUnit.MINUTES.between(noticeDateTime, now);
	    long hoursAgo = ChronoUnit.HOURS.between(noticeDateTime, now);

	    if (minutesAgo < 60) {
	        return minutesAgo + "분 전";
	    } else if (hoursAgo < 24) {
	        return hoursAgo + "시간 전";
	    } else {
	        return noticeDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	    }
	}
    
    /**
     * FAQ 리스트 조회
     */
    @GetMapping
    public String faqList(
        Optional<Integer> page,
        @ModelAttribute("condition") SimpleCondition simpleCondition,
        Model model
    ) {
        PaginationInfo<NoticeVO> paging = new PaginationInfo<>();
        paging.setCurrentPage(page.orElse(1));
        paging.setSimpleCondition(simpleCondition);

        List<NoticeVO> faqList = service.readFaqList(paging);
        
        int FaqTotalCount = service.getFaqTotalCount(simpleCondition);
        
        for (NoticeVO notice : faqList) {
            if (notice.getNoticeDt() != null) {
                notice.setFormattedNoticeDt(formatTimeDifference(notice.getNoticeDt()));
            }
        }

        PaginationRenderer renderer = new DefaultPaginationRenderer();
        String pagingHTML = renderer.renderPagination(paging, null);

        model.addAttribute("faqList", faqList);
        model.addAttribute("pagingHtml", pagingHTML);
        model.addAttribute("FaqTotalCount", FaqTotalCount);

        return "admin/help/faq/faqList";
    }

    /**
     * FAQ 상세 조회
     */
    @GetMapping("/{noticeNo}")
    public String detail(@PathVariable int noticeNo, Model model) {
        NoticeVO faq = service.readFaq(noticeNo); // FAQ도 NoticeVO 사용
        if (faq.getNoticeDt() != null) {
        	faq.setFormattedNoticeDt(formatTimeDifference(faq.getNoticeDt()));
        }
        model.addAttribute("faq", faq);
        return "admin/help/faq/faqDetail";
    }

    /**
     * FAQ 등록 폼
     */
    @GetMapping("/form")
    public String insertForm() {
        return "admin/help/faq/faqForm";
    }

    /**
     * FAQ 등록 처리
     */
    @PostMapping("/form")
    public String insert(
    	@Validated(InsertGroup.class) @ModelAttribute(MODELNAME) NoticeVO faq,
        BindingResult errors,
        RedirectAttributes redirectAttributes
    ) {
        if (!errors.hasErrors()) {
            ServiceResult result = service.createFaq(faq);
            if (result == ServiceResult.OK) {
//                return "redirect:/admin/faq/" + faq.getNoticeNo();
                return "redirect:/admin/faq";
            } else {
                redirectAttributes.addFlashAttribute("message", "등록 실패.");
            }
        } else {
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "faq", errors);
        }
        return "redirect:/admin/faq/form";
    }

    /**
     * FAQ 수정 폼
     */
    @GetMapping("/{noticeNo}/edit")
    public String updateForm(@PathVariable int noticeNo, Model model) {
        NoticeVO faq = service.readFaq(noticeNo); // 기존 데이터 조회
        model.addAttribute(MODELNAME, faq);
        return "admin/help/faq/faqEdit";
    }

    /**
     * FAQ 수정 처리
     */
    @PostMapping("/{noticeNo}/edit")
    public String update(
        @Validated(UpdateGroup.class) @ModelAttribute(MODELNAME) NoticeVO faq,
        BindingResult errors,
        RedirectAttributes redirectAttributes,
        @PathVariable int noticeNo
    ) {
        faq.setNoticeNo(noticeNo);

        if (!errors.hasErrors()) {
            ServiceResult result = service.modifyFaq(faq);
            if (result == ServiceResult.OK) {
            	redirectAttributes.addFlashAttribute("message", "FAQ가 수정되었습니다.");
                return "redirect:/admin/faq";
            } else {
                redirectAttributes.addFlashAttribute("message", "수정 실패. 다시 시도해주세요.");
            }
        } else {
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + MODELNAME, errors);
        }

        return "redirect:/admin/faq/" + noticeNo + "/edit";
    }

    /**
     * FAQ 삭제 처리
     */
    @PostMapping("/{noticeNo}/delete")
    public String delete(
		@PathVariable int noticeNo
		, RedirectAttributes redirectAttributes
    ) {
    	ServiceResult result = service.removeFaq(noticeNo);
        if (result == ServiceResult.OK) {
            redirectAttributes.addFlashAttribute("message", "삭제 완료.");
        } else {
            redirectAttributes.addFlashAttribute("message", "삭제 실패.");
        }

        return "redirect:/admin/faq";
    }
    
    /**
     * FAQ 다중 선택
     */
    @PostMapping("/deleteMultiple")
    public String deleteMultiple(
		@RequestParam("noticeNo") List<Integer> noticeNo
		, RedirectAttributes redirectAttributes
	) {
        if (noticeNo == null || noticeNo.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "삭제할 FAQ를 선택하세요.");
            return "redirect:/admin/faq";
        }
        ServiceResult result = service.removeNotices(noticeNo);
        if (result == ServiceResult.OK) {
            redirectAttributes.addFlashAttribute("message", "선택한 FAQ가 삭제되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("message", "FAQ 삭제 실패.");
        }
        return "redirect:/admin/faq";
    }
}
