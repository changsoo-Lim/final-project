package kr.or.ddit.admin.review.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.ReviewVO;

@Mapper
public interface AdminReviewMapper {
	
	public List<ReviewVO> selectReviewList(PaginationInfo paging);
	
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
	 * 관리자가 승인 
	 * @param reviewNo
	 * @return
	 */
	public int updateReviewStatus(int reviewNo);
}
