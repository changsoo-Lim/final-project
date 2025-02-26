package kr.or.ddit.admin.notice.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.admin.notice.service.AdminHelpService;
import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.paging.renderer.DefaultPaginationRenderer;
import kr.or.ddit.paging.renderer.PaginationRenderer;
import kr.or.ddit.vo.NoticeVO;

@Controller
@RequestMapping("/admin/inquiry")
public class AdminInquiryController {
	public static final String MODELNAME = "inquiry";

    @Autowired
    private AdminHelpService service;

    @ModelAttribute(MODELNAME)
    public NoticeVO inquiry() {
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
    
    // 목록 페이지
    @GetMapping
    public String inquiryList(
    	    Optional<Integer> page,
    	    @ModelAttribute("condition") SimpleCondition simpleCondition,
    	    @RequestParam(required = false) Boolean pending,
    	    Model model
    	) {
    	    PaginationInfo<NoticeVO> paging = new PaginationInfo<>();
    	    paging.setCurrentPage(page.orElse(1));
    	    paging.setSimpleCondition(simpleCondition);
    	    
    	    int InquiryTotalCount = service.getInquiryTotalCount(simpleCondition);
    	    
    	    List<NoticeVO> inquiryList;
    	    if (Boolean.TRUE.equals(pending)) {
    	        // 답변 대기만 가져오기: 전체 데이터를 불러와 필터링
    	        inquiryList = service.readAllPendingInquiries().stream()
    	                .filter(inquiry -> "N".equals(inquiry.getNoticeInquiriesYn()))
    	                .collect(Collectors.toList());
    	    } else {
    	        // 일반 페이징된 데이터 가져오기
    	        inquiryList = service.readInquiryList(paging);
    	    }

    	    for (NoticeVO inquiry : inquiryList) {
    	        if (inquiry.getNoticeDt() != null) {
    	            inquiry.setFormattedNoticeDt(formatTimeDifference(inquiry.getNoticeDt()));
    	        }
    	    }

    	    // 페이징 HTML 생성
    	    if (!Boolean.TRUE.equals(pending)) {
    	        PaginationRenderer renderer = new DefaultPaginationRenderer();
    	        String pagingHtml = renderer.renderPagination(paging, null);
    	        model.addAttribute("pagingHtml", pagingHtml);
    	    } else {
    	        model.addAttribute("pagingHtml", ""); // 답변 대기만 보기 시 페이징 비활성화
    	    }

    	    model.addAttribute("inquiryList", inquiryList);
    	    model.addAttribute("InquiryTotalCount", InquiryTotalCount);
//    	    model.addAttribute("pagingHtml", pagingHtml);
    	    model.addAttribute("pending", pending);
    	    

    	    return "admin/help/inquiry/inquiryList";
    	}
    
    @GetMapping("/{noticeNo}")
    public String inquiryDetail(@PathVariable int noticeNo, Model model) {
        NoticeVO inquiry = service.readInquiry(noticeNo); // 문의 상세 조회
        if (inquiry.getNoticeDt() != null) {
        	inquiry.setFormattedNoticeDt(formatTimeDifference(inquiry.getNoticeDt()));
        }
        model.addAttribute("inquiry", inquiry);
        return "admin/help/inquiry/inquiryDetail";
    }

    @PostMapping(value = "{noticeNo}/answer" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> answerInquiry(
        @PathVariable int noticeNo,
        @RequestBody Map<String, String> requestBody
    ) {
        String noticeInquiriesCont = requestBody.get("noticeInquiriesCont");
        if (noticeInquiriesCont == null || noticeInquiriesCont.isEmpty()) {
            throw new IllegalArgumentException("답변 내용이 비어 있습니다.");
        }
        
        NoticeVO inquiry = new NoticeVO();
        inquiry.setNoticeNo(noticeNo);
        inquiry.setNoticeInquiriesYn("Y");
        inquiry.setNoticeInquiriesCont(noticeInquiriesCont);

        ServiceResult result = service.modifyInquiry(inquiry);
        Map<String, String> response = new HashMap<>();
        response.put("status", result == ServiceResult.OK ? "success" : "fail");
        return response;
    }
    
    @PostMapping(value = "{noticeNo}/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> deleteInquiry(@PathVariable int noticeNo) {
        ServiceResult result = service.removeInquiry(noticeNo);
        Map<String, String> response = new HashMap<>();
        if (result == ServiceResult.OK) {
            response.put("status", "success");
            response.put("message", "문의가 삭제되었습니다.");
        } else {
            response.put("status", "fail");
            response.put("message", "문의 삭제에 실패했습니다.");
        }
        return response; // JSON 응답
    }

    @PostMapping("/deleteMultiple")
    public String deleteMultiple(
		@RequestParam("noticeNo") List<Integer> noticeNo
		, RedirectAttributes redirectAttributes
	) {
        if (noticeNo == null || noticeNo.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "삭제할 문의글을 선택하세요.");
            return "redirect:/admin/inquiry";
        }
        ServiceResult result = service.removeNotices(noticeNo);
        if (result == ServiceResult.OK) {
            redirectAttributes.addFlashAttribute("message", "선택한 문의글이 삭제되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("message", "문의글 삭제 실패.");
        }
        return "redirect:/admin/inquiry";
    }
}
