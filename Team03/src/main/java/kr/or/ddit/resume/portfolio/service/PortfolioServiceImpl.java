package kr.or.ddit.resume.portfolio.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.resume.portfolio.dao.PortfolioMapper;
import kr.or.ddit.vo.PortfolioVO;

@Service
public class PortfolioServiceImpl implements PortfolioService {
	
	@Inject
	PortfolioMapper mapper;
	
	@Override
	public ServiceResult createPortfolio(PortfolioVO portfolioVO) {
		if(mapper.insertPortfolio(portfolioVO) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public List<PortfolioVO> readPortfolioList(String memId) {
		return mapper.selectPortfolioList(memId);
	}

	@Override
	public ServiceResult modifiyPortfolio(PortfolioVO portfolioVO) {
		if(mapper.updatePortfolio(portfolioVO) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public ServiceResult removePortfolio(int portNo) {
		if(mapper.deletePortfolio(portNo) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

}
