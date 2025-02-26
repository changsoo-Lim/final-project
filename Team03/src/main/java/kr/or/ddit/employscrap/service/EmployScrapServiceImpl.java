package kr.or.ddit.employscrap.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.employ.employ.dao.EmployMapper;
import kr.or.ddit.employscrap.dao.EmployScrapMapper;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.EmployVO;
import kr.or.ddit.vo.EmployscrapVO;

@Service
public class EmployScrapServiceImpl implements EmployScrapService {
	
	@Inject
	EmployScrapMapper mapper;
	@Inject
	EmployMapper employMapper;
	
	@Override
	public List<EmployVO> readEmpScrap(String memId, PaginationInfo<EmployscrapVO> paging) {
		List<EmployscrapVO> scrapList = mapper.selectEmpScrap(memId, paging);
		List<EmployVO> employList = new ArrayList<>();
		for (EmployscrapVO scrap : scrapList) {
			employList.add(employMapper.selectEmploy(scrap.getEmployNo(), memId)) ;
		}
		if(scrapList.isEmpty() || scrapList.size() == 0) {
			paging.setTotalRecord(0);
			return employList;
		}
		paging.setTotalRecord(scrapList.get(0).getTotalCnt());
		return employList;
	}

	@Override
	public int updateScrapStatus(EmployscrapVO empScrap) {
		int cnt = 0;
		if(mapper.selectEmpScrapCheck(empScrap.getMemId(), empScrap.getEmployNo()) > 0) {
			cnt = mapper.updateScrapStatus(empScrap);
		}else {
			cnt = mapper.insertEmpScrap(empScrap);
		}
		return cnt;
	}

	@Override
	public int totalEmpScrap(int employNo) {
		return mapper.totalEmpScrap(employNo);
	}
}
