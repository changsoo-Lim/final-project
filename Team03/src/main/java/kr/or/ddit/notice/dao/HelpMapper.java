package kr.or.ddit.notice.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.NoticeVO;

@Mapper
public interface HelpMapper {
	
	/**
	 * 페이징 처리를 위한 검색 결과 레코드 수 조회
	 * @param paging
	 * @return
	 */
	public int selectTotalRecord(@Param("paging") PaginationInfo paging);

	/**
	 * 공지사항 검색 결과 목록조회
	 * @param paging
	 * @return
	 */
	public List<NoticeVO> selectNoticeList(@Param("paging") PaginationInfo paging);

    /**
     * 공지사항 상세조회
     * @param noticeNo
     * @return
     */
    public NoticeVO selectNotice(@Param("noticeNo") int noticeNo);
    
    /**
     * 페이징 처리를 위한 타입 및 검색 결과 레코드 수 조회
     * @param type
     * @param paging
     * @return
     */
    public int selectTotalFaqRecord(@Param("type") String type, @Param("paging") PaginationInfo paging);

    /**
     * FAQ 검색 결과 목록 조회
     * @param type
     * @param paging
     * @return
     */
    public List<NoticeVO> selectFaqListByType(@Param("type") String type, @Param("paging") PaginationInfo paging);

    /**
     * 문의 등록
     * @param inquiry
     * @return int
     */
    public int insertInquiry(NoticeVO noticeType);

    /**
     * 문의 목록 조회
     * @param memId 
     * @param paging
     * @return List<NoticeVO>
     */
    public List<NoticeVO> selectInquiryList(@Param("memId") String memId, @Param("paging") PaginationInfo paging);

    /**
     * 총 문의 수 조회
     * @param memId 
     * @param paging
     * @return int
     */
    public int selectTotalInquiry(@Param("memId") String memId, @Param("paging") PaginationInfo paging);

	/**
	 * 문의 상세 조회
	 * @param noticeNo
	 * @return
	 */
	public NoticeVO selectInquiry(@Param("noticeNo") int noticeNo);
}
