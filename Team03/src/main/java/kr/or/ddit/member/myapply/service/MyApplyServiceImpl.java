package kr.or.ddit.member.myapply.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.member.myapply.dao.MyApplyMapper;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.ApplyVO;
import kr.or.ddit.vo.CodeVO;

@Service
public class MyApplyServiceImpl implements MyApplyService {
	
	@Inject
	private MyApplyMapper mapper;
	
	@Override
	public List<ApplyVO> readMyApplyList(String userId, PaginationInfo paging) {
		if(paging!=null) {
			int totalRecord = mapper.selectTotalRecord(userId, paging);
			paging.setTotalRecord(totalRecord);
		}
		return mapper.selectMyApplyList(userId, paging);
	}
	

	@Override
	public int getTotalCount(String userId, PaginationInfo paging) {
		return mapper.getTotalCount(userId, paging);
	}


	@Override
	public List<CodeVO> readApplyCodeList() {
		return mapper.selectApplyCodeList();
	}
	
}
