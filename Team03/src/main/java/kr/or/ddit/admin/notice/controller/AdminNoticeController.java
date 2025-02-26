package kr.or.ddit.admin.notice.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

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
import org.springframework.web.multipart.MultipartFile;
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
@RequestMapping("/admin/notice")
public class AdminNoticeController {
    public static final String MODELNAME = "notice";

    @Inject
    private AdminHelpService service;

    @ModelAttribute(MODELNAME)
    public NoticeVO notice() {
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
	        // 일주일 이상 지난 경우 날짜로 표시
	        return noticeDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	    }
	}

    /** 공지사항 목록 조회 */
    @GetMapping
    public String list(
            Optional<Integer> page,
            @ModelAttribute("condition") SimpleCondition simpleCondition,
            Model model
    ) {
        PaginationInfo<NoticeVO> paging = new PaginationInfo<>();
        paging.setCurrentPage(page.orElse(1));
        paging.setSimpleCondition(simpleCondition);
        
        List<NoticeVO> noticeList = service.readNoticeList(paging);
        
        int totalCount = service.getTotalCount(simpleCondition);
        
        for (NoticeVO notice : noticeList) {
            if (notice.getNoticeDt() != null) {
                notice.setFormattedNoticeDt(formatTimeDifference(notice.getNoticeDt()));
            }
        }
        
        PaginationRenderer renderer = new DefaultPaginationRenderer();
        String pagingHTML = renderer.renderPagination(paging, null);

        model.addAttribute("noticeList", noticeList);
        model.addAttribute("pagingHTML", pagingHTML);
        model.addAttribute("totalCount", totalCount);

        return "admin/help/notice/noticeList";
    }

    /** 공지사항 상세 조회 */
    @GetMapping("/{noticeNo}")
    public String detail(@PathVariable int noticeNo, Model model) {
        NoticeVO notice = service.readNotice(noticeNo);
        if (notice.getNoticeDt() != null) {
            notice.setFormattedNoticeDt(formatTimeDifference(notice.getNoticeDt()));
        }
        model.addAttribute("notice", notice);
        return "admin/help/notice/noticeDetail";
    }

    /** 공지사항 등록 폼 */
    @GetMapping("/form")
    public String insertForm() {
        return "admin/help/notice/noticeForm";
    }

    /** 공지사항 등록 처리 */
    @PostMapping("/form")
    public String insert(
            @Validated(InsertGroup.class) @ModelAttribute(MODELNAME) NoticeVO notice,
            BindingResult errors,
            @RequestParam("uploadFiles") MultipartFile[] files,
            RedirectAttributes redirectAttributes
    ) throws IOException {
        if (!errors.hasErrors()) {
            // 파일 업로드 처리
            if (files != null && files.length > 0) {
                notice.setUploadFiles(files);
            }

            ServiceResult result = service.createNotice(notice);
            if (result == ServiceResult.OK) {
//                return "redirect:/admin/notice/" + notice.getNoticeNo();
                return "redirect:/admin/notice";
            } else {
                redirectAttributes.addFlashAttribute("message", "공지사항 등록 실패.");
            }
        } else {
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + MODELNAME, errors);
        }
        return "redirect:/admin/notice/form";
    }

    /** 공지사항 수정 폼 */
    @GetMapping("/{noticeNo}/edit")
    public String editForm(@PathVariable int noticeNo, Model model) {
        NoticeVO notice = service.readNotice(noticeNo);
        model.addAttribute(MODELNAME, notice);
        return "admin/help/notice/noticeEdit";
    }

    /** 공지사항 수정 처리 */
    @PostMapping("/{noticeNo}/edit")
    public String update(
            @Validated(UpdateGroup.class) @ModelAttribute(MODELNAME) NoticeVO notice,
            BindingResult errors,
            @RequestParam("uploadFiles") MultipartFile[] files,
            RedirectAttributes redirectAttributes,
            @PathVariable int noticeNo
    ) throws IOException {
        if (!errors.hasErrors()) {
            // 파일 업로드 처리
            if (files != null && files.length > 0) {
                notice.setUploadFiles(files);
            }

            ServiceResult result = service.modifyNotice(notice);
            if (result == ServiceResult.OK) {
            	redirectAttributes.addFlashAttribute("message", "공지사항이 수정되었습니다.");
                // 수정 후 목록 페이지로 이동
                return "redirect:/admin/notice";
            } else {
                redirectAttributes.addFlashAttribute("message", "공지사항 수정 실패.");
            }
        } else {
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + MODELNAME, errors);
        }
        return "redirect:/admin/notice/" + noticeNo + "/edit";
    }

    /** 공지사항 삭제 */
    @PostMapping("/{noticeNo}/delete")
    public String delete(
            @PathVariable int noticeNo,
            RedirectAttributes redirectAttributes
    ) {
        ServiceResult result = service.removeNotice(noticeNo);
        if (result == ServiceResult.OK) {
            redirectAttributes.addFlashAttribute("message", "공지사항이 삭제되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("message", "공지사항 삭제 실패.");
        }
        return "redirect:/admin/notice";
    }
    
    @PostMapping("/deleteMultiple")
    public String deleteMultiple(@RequestParam("noticeNo") List<Integer> noticeNo, RedirectAttributes redirectAttributes) {
        if (noticeNo == null || noticeNo.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "삭제할 공지사항을 선택하세요.");
            return "redirect:/admin/notice";
        }
        ServiceResult result = service.removeNotices(noticeNo);
        if (result == ServiceResult.OK) {
            redirectAttributes.addFlashAttribute("message", "선택한 공지사항이 삭제되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("message", "공지사항 삭제 실패.");
        }
        return "redirect:/admin/notice";
    }
    
    @PostMapping("/{noticeNo}/togglePinned")
    public String togglePinnedStatus(
            @PathVariable int noticeNo,
            RedirectAttributes redirectAttributes
    ) {
        ServiceResult result = service.toggleNoticePinned(noticeNo);
        if (result == ServiceResult.OK) {
            redirectAttributes.addFlashAttribute("message", "공지사항 고정 여부가 변경되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("message", "고정 여부 변경에 실패했습니다.");
        }
        return "redirect:/admin/notice";
    }
}
