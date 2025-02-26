package kr.or.ddit.admin.notice.service;

import java.util.List;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.vo.File_DetailVO;
import kr.or.ddit.vo.NoticeVO;

public interface AdminHelpService {

	/**
	 * 신규 공지사항 생성
	 * 
	 * @param notice
	 * @return 
	 */
	public ServiceResult createNotice(NoticeVO notice);
	
	/**
	 * 특정 공지글 조회
	 * 
	 * @param noticeNo
	 */
	public NoticeVO readNotice(int noticeNo);
	
	/**
	 * 공지사항 목록 조회 (페이징 처리)
	 * @param paginationInfo
	 * @return
	 */
	public List<NoticeVO> readNoticeList(PaginationInfo paging);

	/**
	 * 공지사항글 수정
	 * 
	 * @param notice
	 * @return 
	 */
	public ServiceResult modifyNotice(NoticeVO notice);

	/**
	 * 공지사항글 삭제
	 * @param noticeNo
	 * @return 
	 */
	public ServiceResult removeNotice(int noticeNo);

	/**
	 * 파일 다운로드
	 * 
	 * @param atchFileId
	 * @param fileSn
	 * @return
	 */
	public File_DetailVO download(int atchFileId, int fileSn);

	/**
	 * 파일 한건 삭제
	 * 
	 * @param atchFileId
	 * @param fileSn
	 */
	public void removeFile(int atchFileId, int fileSn);

	/**
	 * 공지사항 고정 여부
	 * 
	 * @param noticeNo
	 * @return
	 */
	public ServiceResult toggleNoticePinned(int noticeNo);

	/**
	 * 선택 공지사항 삭제
	 * @param noticeNo
	 * @return
	 */
	public ServiceResult removeNotices(List<Integer> noticeNo);

	/**
	 * FAQ 목록 조회 (페이징 처리)
	 * @param paging
	 * @return
	 */
	public List<NoticeVO> readFaqList(PaginationInfo paging);

	/**
	 * 특정 FAQ 조회
	 * @param noticeNo
	 * @return
	 */
	public NoticeVO readFaq(int noticeNo);

	/**
	 * FAQ 생성
	 * @param faq
	 * @return
	 */
	public ServiceResult createFaq(NoticeVO faq);

	/**
	 * FAQ 수정
	 * @param faq
	 * @return
	 */
	public ServiceResult modifyFaq(NoticeVO faq);
	
	/**
	 * FAQ 삭제
	 * @param noticeNo
	 * @return
	 */
	public ServiceResult removeFaq(int noticeNo);

	/**
	 * 문의 목록 조회 (페이징 처리)
	 * @param paging
	 * @return
	 */
	public List<NoticeVO> readInquiryList(PaginationInfo paging);

	/**
	 * 특정 문의 조회
	 * @param noticeNo
	 * @return
	 */
	public NoticeVO readInquiry(int noticeNo);

	/**
	 * 문의 수정
	 * @param inquiry
	 * @return
	 */
	public ServiceResult modifyInquiry(NoticeVO inquiry);

	/**
	 * 문의 삭제
	 * @param noticeNo
	 * @return
	 */
	public ServiceResult removeInquiry(int noticeNo);

	/**
	 * 답변 대기 중인 모든 문의 조회
	 * @return
	 */
	public List<NoticeVO> readAllPendingInquiries();

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

//	public ServiceResult removeFaqs(List<Integer> noticeNo);


}
