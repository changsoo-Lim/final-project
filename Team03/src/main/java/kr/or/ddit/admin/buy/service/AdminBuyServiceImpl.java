package kr.or.ddit.admin.buy.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.admin.buy.dao.AdminBuyMapper;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.BuyVO;

@Service
public class AdminBuyServiceImpl implements AdminBuyService{
	
	@Inject
	AdminBuyMapper mapper;

	@Override
	public List<BuyVO> readCompantBuyList(PaginationInfo paging) {
		if(paging!=null) {
			int totalRecord = mapper.selectTotalRecord(paging);
			paging.setTotalRecord(totalRecord);
		}
		return mapper.selectCompantBuyList(paging);
	}

	@Override
	public int getTotalCount(PaginationInfo paging) {
		return mapper.getTotalCount(paging);
	}

}
