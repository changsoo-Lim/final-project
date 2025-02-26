package kr.or.ddit.member.myapply.service;

import java.util.List;

import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.ApplyVO;
import kr.or.ddit.vo.CodeVO;

public interface MyApplyService {
	
	/**
	 * 나의 지원 내역 조회
	 * @param paging
	 * @return
	 */
	public List<ApplyVO> readMyApplyList(String userId, PaginationInfo paging);
	/**
	 * 게시글 목록 총 개수
	 * @param simpleCondition
	 * @return
	 */
	public int getTotalCount(String userId, PaginationInfo paging);
	
	public List<CodeVO> readApplyCodeList();
}
