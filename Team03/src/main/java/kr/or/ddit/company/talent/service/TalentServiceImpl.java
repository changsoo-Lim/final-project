package kr.or.ddit.company.talent.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.company.talent.dao.TalentMapper;
import kr.or.ddit.employ.employ.dao.EmployMapper;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.resume_view.dao.Resume_ViewMapper;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.EmployVO;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.Resume_ViewVO;

@Service
public class TalentServiceImpl implements TalentService {

	@Inject
	private TalentMapper dao;
	@Inject
	private Resume_ViewMapper reDao;
	@Inject
	private EmployMapper empDao;
	
	@Override
	public List<CodeVO> readCode() {
		return dao.selectCode();
	}

	@Override
	public List<MemberVO> searchTalent(PaginationInfo<MemberVO> paging) {
			
		if (paging != null) {
			int totalRecord = dao.selectTotalRecord(paging);
			paging.setTotalRecord(totalRecord);
		}
		
		return dao.searchTalent(paging);
	}

	@Override
	public MemberVO readMember(Resume_ViewVO resumeViewVO) {
		int cnt = reDao.resumeCheck(resumeViewVO);
		
		
		
		if(cnt<=0) {
			reDao.resumeViewInsert(resumeViewVO);
		}
		return dao.selectMember(resumeViewVO.getMemId());
	}

	@Override
	public List<MemberVO> talentList(List<String> memIdList) {
		return dao.talentList(memIdList);
	}

	@Override
	public List<EmployVO> getEmploy(String compId , String talentId) {
		return dao.selectEmployList(compId, talentId);
	}
}
