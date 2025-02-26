package kr.or.ddit.review.service;

import java.util.List;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.vo.EmployVO;
import kr.or.ddit.vo.ReviewVO;

public interface ReviewService {
	
	public ReviewVO readReviewDetail(String reviewId); 
	
	/**
	 * 면접 후기 전체 리스트
	 * @return
	 */
	public List<ReviewVO> readInterviewReviewList(PaginationInfo paging);
	
	/**
	 * 게시글 목록 총 개수 
	 * @param condition
	 * @return
	 */
	public int getTotalCount(PaginationInfo paging);
	
	/**
	 * 나의 면접 후기 리스트
	 * @return
	 */
	public List<ReviewVO> readMyReviewList(String memId,PaginationInfo paging);
	
	/**
	 * 내가 지원한 공고의 면접을 본 기업 리스트
	 * @param memId
	 * @return
	 */
	public List<EmployVO> readMyApplyList(String memId);
	
	/**
	 * 면접 후기 등록
	 * @param reviewVO
	 * @return
	 */
	public ServiceResult createReview(ReviewVO reviewVO);
	
}
