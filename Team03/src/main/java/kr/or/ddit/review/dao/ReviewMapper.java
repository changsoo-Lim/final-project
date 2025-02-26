package kr.or.ddit.review.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.vo.EmployVO;
import kr.or.ddit.vo.ReviewVO;

@Mapper
public interface ReviewMapper {
	
	public List<ReviewVO> selectReviewList(String compId);
	
	public ReviewVO selectReview(String reviewId);
	
	/**
	 * 면접 후기 전체 리스트
	 * @return
	 */
	public List<ReviewVO> selectInterviewReviewList(PaginationInfo paging);
	
	/**
	 * 페이징 처리를 위한 검색 결과 레코드 수 조회
	 * @param paging
	 * @return
	 */
	public int selectTotalRecord(PaginationInfo paging);
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
	public List<ReviewVO> selectMyReviewList(@Param("memId") String memId, @Param("paging") PaginationInfo paging);
	
	/**
	 * 내가 지원한 공고의 면접을 본 기업 리스트
	 * @param memId
	 * @return
	 */
	public List<EmployVO> selectMyApplyList(String memId);
	
	/**
	 * 면접 후기 등록
	 * @param reviewVO
	 * @return
	 */
	public int insertReview(ReviewVO reviewVO);
}
