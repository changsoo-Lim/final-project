package kr.or.ddit.resume.portfolio.service;

import java.util.List;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.vo.PortfolioVO;

public interface PortfolioService {
	
	public ServiceResult createPortfolio(PortfolioVO portfolioVO);
	
	public List<PortfolioVO> readPortfolioList(String memId);
	
	public ServiceResult modifiyPortfolio(PortfolioVO portfolioVO);
	
	public ServiceResult removePortfolio(int portNo);
}
