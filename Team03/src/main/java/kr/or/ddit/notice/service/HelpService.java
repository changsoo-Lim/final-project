package kr.or.ddit.notice.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.NoticeVO;

public interface HelpService {
    /**
	 * @param paginationInfo
	 * @return
	 */
	public List<NoticeVO> readNoticeList(PaginationInfo paging);

    /** 
     * 특정 글 조회
     * @param noticeNo
     * @return
     */
    public NoticeVO readNotice(int noticeNo);
    
    /**
     * FAQ 목록 조회 (타입별)
     * @param type
     * @param paging
     * @return List<NoticeVO>
     */
    List<NoticeVO> readFaqList(String type, PaginationInfo paging);
    
    /**
     * 문의 등록
     * @param inquiry
     * @return ServiceResult
     */
    public ServiceResult createInquiry(NoticeVO noticeType);

    /**
     * 문의 목록 조회
     * @param userId 
     * @param paging
     * @return List<NoticeVO>
     */
    public List<NoticeVO> readInquiryList(String userId, PaginationInfo paging);
    
    /**
     * 문의 상세 조회
     * @param noticeNo
     * @return
     */
    public NoticeVO readInquiry(@Param("noticeNo") int noticeNo);
	
}
