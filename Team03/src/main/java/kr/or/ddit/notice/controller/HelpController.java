package kr.or.ddit.notice.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import kr.or.ddit.notice.service.HelpService;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.paging.renderer.DefaultPaginationRenderer;
import kr.or.ddit.paging.renderer.PaginationRenderer;
import kr.or.ddit.vo.NoticeVO;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping("/help")
public class HelpController {

	@Autowired
	private HelpService helpService;
	
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
	        return noticeDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	    }
	}

	@GetMapping("/faq/{type}")
	public String faq(
		@PathVariable("type") String type,
        Optional<Integer> page,
        @ModelAttribute("condition") SimpleCondition simpleCondition,
        Model model
    ) {
	    String noticeType = null;
	    if ("user".equals(type)) {
	        noticeType = "NT02"; // 개인회원
	    } else if ("corporation".equals(type)) {
	        noticeType = "NT03"; // 기업회원
	    } else {
	        throw new IllegalArgumentException("잘못된 type 값: " + type);
	    }

	    model.addAttribute("faqType", type);
	    model.addAttribute("currentPage", "/help/faq/" + type);

	    PaginationInfo paging = new PaginationInfo();
	    paging.setCurrentPage(page.orElse(1));
	    paging.setSimpleCondition(simpleCondition);

	    // 변환된 type 값 사용
	    List<NoticeVO> faqList = helpService.readFaqList(noticeType, paging);

	    PaginationRenderer renderer = new DefaultPaginationRenderer();
	    String pagingHTML = renderer.renderPagination(paging, null);

	    model.addAttribute("faqList", faqList);
	    model.addAttribute("pagingHtml", pagingHTML);
	    model.addAttribute("contentPage", "/WEB-INF/views/stackUp/main/help/faq/faqListContent.jsp");
	    
	    return "main/help/layout";
    }

	@GetMapping("/notice")
	public String notice(
			Optional<Integer> page
			, @ModelAttribute("condition") SimpleCondition simpleCondition
			, Model model
	) {
		PaginationInfo paging = new PaginationInfo();
		paging.setCurrentPage(page.orElse(1));
		paging.setSimpleCondition(simpleCondition);

		List<NoticeVO> noticeList = helpService.readNoticeList(paging);

		for (NoticeVO notice : noticeList) {
	        notice.setFormattedNoticeDt(formatTimeDifference(notice.getNoticeDt()));
	    }
		
		PaginationRenderer renderer = new DefaultPaginationRenderer();
		String pagingHTML = renderer.renderPagination(paging, null);

		model.addAttribute("currentPage", "/help/notice");
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("pagingHtml", pagingHTML);
		return "main/help/notice/noticeList";
	}

	@GetMapping("/notice/{noticeNo}")
	public String noticeDetail(@PathVariable int noticeNo, Model model) {
		NoticeVO notice = helpService.readNotice(noticeNo);
		notice.setFormattedNoticeDt(formatTimeDifference(notice.getNoticeDt()));
		model.addAttribute("notice", notice);
		return "main/help/notice/noticeDetail";
	}

	/**
	 * 문의 등록 폼
	 */
	@GetMapping("/inquiry")
	public String inquiryForm(Model model) {
		model.addAttribute("currentPage", "/help/inquiry");
		model.addAttribute("noticeType", new NoticeVO());
		return "main/help/inquiry/inquiryForm";
	}

	/**
	 * 문의 등록 처리
	 */
	@PostMapping("/inquiry")
	public String submitInquiry(
		@Valid @ModelAttribute("noticeType") NoticeVO noticeType
		, BindingResult errors
		, RedirectAttributes redirectAttributes
		, Authentication authentication
	) {
		String userId = ((UserDetails) authentication.getPrincipal()).getUsername();
		noticeType.setMemId(userId);
		noticeType.setNoticeType("NT05");

		redirectAttributes.addFlashAttribute("noticeType", noticeType);

		String lvn = null;
		log.info(noticeType.toString());
		log.info(errors.toString());
		if (!errors.hasErrors()) {
			ServiceResult result = helpService.createInquiry(noticeType);
			switch (result) {
			case OK:
				lvn = "redirect:/help/inquiry/list";
				break;
			default:
				lvn = "redirect:/help/inquiry";
				redirectAttributes.addFlashAttribute("message", "서버 오류, 잠시 후 다시 시도해 주세요.");
				break;
			}
		} else {
			// 검증 실패 시 에러 정보 추가
			String errAttrName = BindingResult.MODEL_KEY_PREFIX + "noticeType";
			redirectAttributes.addFlashAttribute(errAttrName, errors);
			lvn = "redirect:/help/inquiry"; // 다시 등록 폼으로 리다이렉트
		}
		return lvn;
	}

	/**
	 * 문의 내역 조회
	 * @param inquiry 
	 */
	@GetMapping("inquiry/list")
	public String inquiryList(
		Optional<Integer> page
		, @ModelAttribute("condition") SimpleCondition simpleCondition
		, Model model
		, Authentication authentication
	) {
		if (authentication == null || authentication.getPrincipal() == null) {
	        return "redirect:/login";  // 로그인 페이지로 리다이렉트
	    }
		
		String userId = ((UserDetails) authentication.getPrincipal()).getUsername();
		
		if (simpleCondition == null) {
			simpleCondition = new SimpleCondition(); // 기본값 생성
		}
		
		PaginationInfo paging = new PaginationInfo();
		paging.setCurrentPage(page.orElse(1));
		paging.setSimpleCondition(simpleCondition);

		List<NoticeVO> inquiryList = helpService.readInquiryList(userId, paging);
		
		for (NoticeVO inquiry : inquiryList) {
	        inquiry.setFormattedNoticeDt(formatTimeDifference(inquiry.getNoticeDt()));
	    }
		
		PaginationRenderer renderer = new DefaultPaginationRenderer();
		String pagingHTML = renderer.renderPagination(paging, null);
		
		model.addAttribute("currentPage", "/help/inquiry/list");
		model.addAttribute("inquiryList", inquiryList);
		model.addAttribute("pagingHtml", pagingHTML);

		return "main/help/inquiry/inquiryList";
	}

	/**
	 * 문의 상세 조회
	 */
	@GetMapping("inquiry/{noticeNo}")
	public String inquiryDetail(@PathVariable Integer noticeNo, Model model) {
	    NoticeVO inquiry = helpService.readInquiry(noticeNo);
	    inquiry.setFormattedNoticeDt(formatTimeDifference(inquiry.getNoticeDt()));

	    if (inquiry.getNoticeInquiriesDt() != null) {
	        inquiry.setFormattedInquiriesDt(
	            inquiry.getNoticeInquiriesDt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
	        );
	    }

	    model.addAttribute("inquiry", inquiry);
	    model.addAttribute("currentPage", "/help/inquiry/list");
	    return "main/help/inquiry/inquiryDetail";
	}

}
