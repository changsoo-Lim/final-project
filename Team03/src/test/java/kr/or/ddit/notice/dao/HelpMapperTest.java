package kr.or.ddit.notice.dao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
class HelpMapperTest {

    @Autowired
    HelpMapper mapper;

    NoticeVO notice;

    @BeforeEach
    void beforeEach() {
        notice = new NoticeVO();
        notice.setNoticeTitle("테스트 제목");
        notice.setNoticeCont("테스트 내용");
        notice.setNoticeType("NT01");
        notice.setNoticeDel("N");
        notice.setMemId("testUser");
        assertDoesNotThrow(() -> mapper.insertInquiry(notice));
    }

    @Test
    void testInsertInquiry() {
        assertDoesNotThrow(() -> {
            NoticeVO newNotice = new NoticeVO();
            newNotice.setNoticeTitle("새 공지사항 제목");
            newNotice.setNoticeCont("새 공지사항 내용");
            newNotice.setNoticeType("NT01");
            newNotice.setNoticeDel("N");
            newNotice.setMemId("newUser");
            mapper.insertInquiry(newNotice);
        });
    }

    @Test
    void testSelectNotice() {
        assertDoesNotThrow(() -> {
            NoticeVO selectedNotice = mapper.selectNotice(notice.getNoticeNo());
            assertEquals(notice.getNoticeTitle(), selectedNotice.getNoticeTitle());
        });
    }

    @Test
    void testSelectNoticeList() {
        assertDoesNotThrow(() -> {
        	PaginationInfo paging = new PaginationInfo();
            paging.setCurrentPage(1);
            SimpleCondition simpleCondition = new SimpleCondition();
            simpleCondition.setSearchWord("테스트");
            paging.setSimpleCondition(simpleCondition);
            List<NoticeVO> noticeList = mapper.selectNoticeList(paging);
            assertNotNull(noticeList);
        });
    }

    @Test
    void testSelectTotalRecord() {
        assertDoesNotThrow(() -> {
        	PaginationInfo paging = new PaginationInfo();
            paging.setCurrentPage(1);
            int totalRecords = mapper.selectTotalRecord(paging);
            assertTrue(totalRecords >= 0);
        });
    }

    @Test
    void testSelectFaqListByType() {
        assertDoesNotThrow(() -> {
            notice.setNoticeType("NT02");
            mapper.insertInquiry(notice);
            PaginationInfo paging = new PaginationInfo();
            paging.setCurrentPage(1);
            List<NoticeVO> faqList = mapper.selectFaqListByType("NT02", paging);
            assertNotNull(faqList);
        });
    }

    @Test
    void testSelectTotalFaqRecord() {
        assertDoesNotThrow(() -> {
        	PaginationInfo paging = new PaginationInfo();
            paging.setCurrentPage(1);
            int totalFaqRecords = mapper.selectTotalFaqRecord("NT02", paging);
            assertTrue(totalFaqRecords >= 0);
        });
    }

    @Test
    void testSelectInquiryList() {
        assertDoesNotThrow(() -> {
            notice.setNoticeType("NT05");
            mapper.insertInquiry(notice);
            PaginationInfo paging = new PaginationInfo();
            paging.setCurrentPage(1);

            String memId = "testUser"; // 테스트용 사용자 ID
            List<NoticeVO> inquiryList = mapper.selectInquiryList(memId, paging);
            assertNotNull(inquiryList);
            assertTrue(inquiryList.size() > 0);
        });
    }

    @Test
    void testSelectTotalInquiry() {
        assertDoesNotThrow(() -> {
            PaginationInfo paging = new PaginationInfo();
            paging.setCurrentPage(1);

            String memId = "testUser"; // 테스트용 사용자 ID
            int totalInquiries = mapper.selectTotalInquiry(memId, paging);
            assertTrue(totalInquiries >= 0);
        });
    }


    @Test
    void testSelectInquiry() {
        assertDoesNotThrow(() -> {
            notice.setNoticeType("NT05");
            mapper.insertInquiry(notice);
            NoticeVO selectedInquiry = mapper.selectInquiry(notice.getNoticeNo());
            assertEquals(notice.getNoticeTitle(), selectedInquiry.getNoticeTitle());
        });
    }
}
