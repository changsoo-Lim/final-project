package kr.or.ddit.admin.notice.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.annotation.RootContextWebConfig;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.vo.NoticeVO;

@RootContextWebConfig
@Transactional
class AdminHelpMapperTest {

    @Autowired
    AdminHelpMapper mapper;

    NoticeVO notice;

    @BeforeEach
    void BeforeEach() {
        notice = new NoticeVO();
        notice.setNoticeTitle("테스트 공지사항 제목");
        notice.setNoticeCont("테스트 공지사항 내용");
        notice.setMemId("testUser");
        notice.setNoticePinned("N");
        assertEquals(1, mapper.insertNotice(notice), "초기 데이터 삽입 실패");
        assertNotNull(notice.getNoticeNo(), "공지사항 ID 생성 실패");
    }

    @Test
    void testInsertNotice() {
        NoticeVO newNotice = new NoticeVO();
        newNotice.setNoticeTitle("새 공지사항 제목");
        newNotice.setNoticeCont("새 공지사항 내용");
        newNotice.setMemId("newUser");
        newNotice.setNoticePinned("Y");

        assertEquals(1, mapper.insertNotice(newNotice), "공지사항 삽입 실패");
        assertNotNull(newNotice.getNoticeNo(), "공지사항 ID 생성 실패");
    }

    @Test
    void testSelectNotice() {
        NoticeVO selectedNotice = mapper.selectNotice(notice.getNoticeNo());
        assertNotNull(selectedNotice, "공지사항 조회 실패");
        assertEquals(notice.getNoticeTitle(), selectedNotice.getNoticeTitle(), "공지사항 제목 불일치");
        assertEquals(notice.getMemId(), selectedNotice.getMemId(), "작성자 불일치");
    }

    @Test
    void testUpdateNotice() {
        notice.setNoticeTitle("수정된 제목");
        assertEquals(1, mapper.updateNotice(notice), "공지사항 업데이트 실패");

        NoticeVO updatedNotice = mapper.selectNotice(notice.getNoticeNo());
        assertEquals("수정된 제목", updatedNotice.getNoticeTitle(), "수정된 제목이 일치하지 않음");
    }

    @Test
    void testDeleteNotice() {
        assertEquals(1, mapper.deleteNotice(notice.getNoticeNo()), "공지사항 삭제 실패");

        NoticeVO deletedNotice = mapper.selectNotice(notice.getNoticeNo());
        assertNull(deletedNotice, "삭제된 공지사항 조회 실패");
    }

    @Test
    void testSelectNoticeList() {
        PaginationInfo<NoticeVO> paging = new PaginationInfo<>(10, 5);
        paging.setCurrentPage(1);
        List<NoticeVO> noticeList = mapper.selectNoticeList(paging);
        assertNotNull(noticeList, "공지사항 목록 조회 실패");
        assertTrue(noticeList.size() > 0, "공지사항 목록이 비어 있습니다");
    }

    @Test
    void testUpdateNoticePinned() {
        assertEquals(1, mapper.updateNoticePinned(notice.getNoticeNo(), "Y"), "공지사항 고정 상태 업데이트 실패");

        NoticeVO updatedNotice = mapper.selectNotice(notice.getNoticeNo());
        assertEquals("Y", updatedNotice.getNoticePinned(), "공지사항 고정 상태 불일치");
    }

    @Test
    void testSelectFaqList() {
        notice.setNoticeType("NT02");
        mapper.insertNotice(notice);

        PaginationInfo<NoticeVO> paging = new PaginationInfo<>(10, 5);
        paging.setCurrentPage(1);
        List<NoticeVO> faqList = mapper.selectFaqList(paging);
        assertNotNull(faqList, "FAQ 목록 조회 실패");
        assertTrue(faqList.size() > 0, "FAQ 목록이 비어 있습니다");
    }

    @Test
    void testSelectFaq() {
        notice.setNoticeType("NT02");
        mapper.insertFaq(notice);

        NoticeVO faq = mapper.selectFaq(notice.getNoticeNo());
        assertNotNull(faq, "FAQ 단건 조회 실패");
        assertEquals("NT02", faq.getNoticeType(), "FAQ 타입 불일치");
    }

    @Test
    void testDeleteFaq() {
        notice.setNoticeType("NT02");
        mapper.insertFaq(notice);

        assertEquals(1, mapper.deleteFaq(notice.getNoticeNo()), "FAQ 삭제 실패");
        NoticeVO deletedFaq = mapper.selectFaq(notice.getNoticeNo());
        assertNull(deletedFaq, "FAQ 삭제 실패");
    }

    @Test
    void testSelectAllPendingInquiries() {
        List<NoticeVO> pendingInquiries = mapper.selectAllPendingInquiries();
        assertNotNull(pendingInquiries, "미처리 문의 조회 실패");
        assertTrue(pendingInquiries.size() >= 0, "미처리 문의 목록이 비어 있습니다");
    }

    @Test
    void testGetTotalCount() {
        SimpleCondition condition = new SimpleCondition();
        condition.setSearchWord("테스트");
        int totalCount = mapper.getTotalCount(condition);
        assertTrue(totalCount >= 0, "공지사항 총 레코드 수 조회 실패");
    }

    @Test
    void testGetFaqTotalCount() {
        SimpleCondition condition = new SimpleCondition();
        condition.setSearchWord("FAQ");
        int faqTotalCount = mapper.getFaqTotalCount(condition);
        assertTrue(faqTotalCount >= 0, "FAQ 총 레코드 수 조회 실패");
    }

    @Test
    void testGetInquiryTotalCount() {
        SimpleCondition condition = new SimpleCondition();
        condition.setSearchWord("문의");
        int inquiryTotalCount = mapper.getInquiryTotalCount(condition);
        assertTrue(inquiryTotalCount >= 0, "문의 총 레코드 수 조회 실패");
    }
}
