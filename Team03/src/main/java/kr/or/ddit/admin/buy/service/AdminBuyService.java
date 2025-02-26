package kr.or.ddit.admin.buy.service;

import java.util.List;

import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.BuyVO;

public interface AdminBuyService {
	
	/**
	 * 기업들 구매 내역
	 * @param paging
	 * @return
	 */
	public List<BuyVO> readCompantBuyList(PaginationInfo paging);
	
	/**
	 * 게시글 목록 총 개수 
	 * @param condition
	 * @return
	 */
	public int getTotalCount(PaginationInfo paging);
}
