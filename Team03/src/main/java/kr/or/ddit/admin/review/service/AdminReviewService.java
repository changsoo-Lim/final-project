package kr.or.ddit.admin.review.service;

import java.util.List;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.ReviewVO;

public interface AdminReviewService {
	
	public List<ReviewVO> readReviewList(PaginationInfo paging);
	
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
	public ServiceResult modifiyReviewStatus(int reviewNo);
}
