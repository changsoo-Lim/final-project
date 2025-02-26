package kr.or.ddit.prod.buy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.BuyVO;
import kr.or.ddit.vo.EmployVO;

@Mapper
public interface BuyMapper {
	
	/**
	 * @param compId
	 * @return
	 */
	public List<BuyVO> selectBuyList(String compId);
	
	/**
	 * @param buyNo
	 * @return
	 */
	public BuyVO selectBuy(int buyNo);
	
	/**
	 * @param buyVO
	 * @return
	 */
	public int insertBuy(BuyVO buyVO);
	
	/**
	 * 채용공고 상품을 구매하지 않은 공고 리스트
	 * @param compId
	 * @return
	 */
	public List<EmployVO> empList(String compId);
	
}
