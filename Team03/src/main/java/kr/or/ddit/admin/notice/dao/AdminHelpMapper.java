package kr.or.ddit.admin.notice.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.vo.NoticeVO;

@Mapper
public interface AdminHelpMapper {

    /** 
     * 공지사항 등록
     * @param notice
     * @return
     */
    public int insertNotice(NoticeVO notice);

    /** 
     * 공지사항 상세 조회
     * @param noticeNo
     * @return
     */
    public NoticeVO selectNotice(int noticeNo);

    /**
     * 공지사항 목록 수 조회
     * @param paging
     * @return
     */
    public int selectTotalRecord(PaginationInfo<NoticeVO> paging);

    /**
     * 공지사항 목록 조회 (페이징)
     * @param paging
     * @return
     */
    public List<NoticeVO> selectNoticeList(PaginationInfo<NoticeVO> paging);

    /**
     * 공지사항 수정
     * @param notice
     * @return
     */
    public int updateNotice(NoticeVO notice);

    /**
     * 공지사항 삭제
     * @param noticeNo
     * @return
     */
    public int deleteNotice(int noticeNo);

	/**
	 * 공지사항 고정여부
	 * @param noticeNo
	 * @param newPinnedStatus
	 * @return
	 */
	public int updateNoticePinned(@Param("noticeNo") int noticeNo, @Param("newPinnedStatus") String newPinnedStatus);

	/**
	 * 공지사항 선택 삭제
	 * @param noticeNo
	 * @return
	 */
	public int deleteNotices(List<Integer> noticeNo);
	
	/**
	 * FAQ 총 레코드 수 조회
	 * @param paging
	 * @return
	 */
	public int selectFaqTotalCount(PaginationInfo paging);
	
	/**
	 * FAQ 목록 조회
	 * @param paging
	 * @return
	 */
	public List<NoticeVO> selectFaqList(PaginationInfo paging);
    
	/**
	 * FAQ 상세 조회
	 * @param noticeNo
	 * @return
	 */
	public NoticeVO selectFaq(int noticeNo);
    
	/**
	 * FAQ 등록
	 * @param faq
	 * @return
	 */
	public int insertFaq(NoticeVO faq);
    
	/**
	 * FAQ 수정
	 * @param faq
	 * @return
	 */
	public int updateFaq(NoticeVO faq);
    
	/**
	 * FAQ 삭제
	 * @param noticeNo
	 * @return
	 */
	public int deleteFaq(int noticeNo);

	/**
	 * 문의 총 레코드 수 조회
	 * @param paging
	 * @return
	 */
	public int selectInquiryTotalCount(PaginationInfo paging);

	/**
	 * 문의 목록 조회
	 * @param paging
	 * @return
	 */
	public List<NoticeVO> selectInquiryList(PaginationInfo paging);

	/**
	 * 문의 상세 조회
	 * @param noticeNo
	 * @return
	 */
	public NoticeVO selectInquiry(int noticeNo);

	/**
	 * 문의 수정
	 * @param inquiry
	 * @return
	 */
	public int updateInquiry(NoticeVO inquiry);

	/**
	 * 문의 삭제
	 * @param noticeNo
	 * @return
	 */
	public int deleteInquiry(int noticeNo);

	/**
	 * 답변 대기 중인 문의 목록 조회
	 * @return
	 */
	public List<NoticeVO> selectAllPendingInquiries();

	/** 
	 * 공지사항 총 개수 조회
	 * @param simpleCondition
	 * @return
	 */
	public int getTotalCount(SimpleCondition simpleCondition);

	/**
	 * FAQ 총 개수 조회
	 * @param simpleCondition
	 * @return
	 */
	public int getFaqTotalCount(SimpleCondition simpleCondition);

	/**
	 * 문의 총 개수 조회
	 * @param simpleCondition
	 * @return
	 */
	public int getInquiryTotalCount(SimpleCondition simpleCondition);

//	public Map<String, Integer> getInquiryStatusCounts();

	
//	public int deleteFaqs(List<Integer> noticeNo);
}
