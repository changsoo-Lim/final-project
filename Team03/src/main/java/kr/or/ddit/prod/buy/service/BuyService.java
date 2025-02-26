package kr.or.ddit.prod.buy.service;

import java.util.List;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.vo.BuyVO;
import kr.or.ddit.vo.EmployVO;

public interface BuyService {
	
	/**
	 * @param compId
	 * @return
	 */
	public List<BuyVO> readBuyList(String compId);
	
	/**
	 * @param buyNo
	 * @return
	 */
	public BuyVO readBuy(int buyNo);
	
	/**
	 * @param buyVO
	 * @return
	 */
	public ServiceResult createBuy(BuyVO buyVO);
	
	/**
	 * 채용공고 상품을 구매하지 않은 공고 리스트
	 * @param compId
	 * @return
	 */
	public List<EmployVO> empList(String compId);
}
