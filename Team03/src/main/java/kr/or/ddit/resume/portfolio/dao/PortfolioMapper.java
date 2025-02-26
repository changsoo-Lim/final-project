package kr.or.ddit.resume.portfolio.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.PortfolioVO;

@Mapper
public interface PortfolioMapper {

	/**
	 * @param portfolioVO
	 * @return
	 */
	public int insertPortfolio(PortfolioVO portfolioVO);
	
	public List<PortfolioVO> selectPortfolioList(String memId);
	
	public int updatePortfolio(PortfolioVO portfolioVO);
	
	public int deletePortfolio(int portNo);
}
