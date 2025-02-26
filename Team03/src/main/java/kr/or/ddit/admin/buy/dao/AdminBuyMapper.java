package kr.or.ddit.admin.buy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.BuyVO;

@Mapper
public interface AdminBuyMapper {
	
	/**
	 * 기업들 구매 내역
	 * @param paging
	 * @return
	 */
	public List<BuyVO> selectCompantBuyList(PaginationInfo paging);
	
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
}
