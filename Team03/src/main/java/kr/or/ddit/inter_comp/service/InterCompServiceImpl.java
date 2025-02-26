package kr.or.ddit.inter_comp.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.inter_comp.dao.Inter_CompMapper;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.Inter_CompVO;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.Work_CondVO;

@Service
public class InterCompServiceImpl implements InterCompService {
	
	@Inject
	Inter_CompMapper mapper;
	
	@Override
	public List<Inter_CompVO> readInterComp(String memId) {
		return mapper.selectInterComp(memId);
	}

	@Override
	public int updateScrapStatus(Inter_CompVO interComp) {
		int cnt = 0;
		if(mapper.selectInterCompCheck(interComp.getMemId(), interComp.getCompId()) > 0) {
			cnt = mapper.updateScrapStatus(interComp);
		}else {
			cnt = mapper.insertInterComp(interComp);
		}
		return cnt;
	}

	@Override
	public int totalInterComp(String compId) {
		return mapper.totalInterComp(compId);
	}


	@Override
	public List<Inter_CompVO> selectMemList(String username, PaginationInfo<Inter_CompVO> pagination) {
		int totalRecord = 0;
		if (pagination != null) {
			totalRecord = mapper.selectTotalRecorde(username);
			pagination.setTotalRecord(totalRecord);
		}
		
		List<Inter_CompVO> interList = mapper.selectMemList(username, pagination);
		
		if(!interList.isEmpty()) {
			interList.get(0).setTotal(totalRecord);
		}
		for (Inter_CompVO inter : interList) {
			 inter.getMember().setWorkCondList(mapper.workCondList(inter.getMember().getMemId()));
			 for(Work_CondVO condVO : inter.getMember().getWorkCondList()) {
				 condVO.setWorkCity(mapper.workCityList(condVO.getWorkCondNo()));
			 }
		} 
		return interList;
	}
}
