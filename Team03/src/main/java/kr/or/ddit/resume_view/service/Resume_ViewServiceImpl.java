package kr.or.ddit.resume_view.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.swing.undo.AbstractUndoableEdit;

import org.springframework.stereotype.Service;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.company.talent.dao.TalentMapper;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.resume_view.dao.Resume_ViewMapper;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.Resume_ViewVO;

@Service
public class Resume_ViewServiceImpl implements Resume_ViewService {
	
	@Inject
	private Resume_ViewMapper dao;
	
	@Inject
	private TalentMapper tDao;
	
	@Override
	public ServiceResult resume_ViewInser(Resume_ViewVO resumeView) {
		
		return null;
	}

	@Override
	public List<MemberVO> resumeViewSelectList(PaginationInfo<Resume_ViewVO> paging, String compId) {
		
		if (paging != null) {
			int totalRecord = dao.selectTotalRecorde(compId);
			paging.setTotalRecord(totalRecord);
		}
		
		List<String> memIdList = new ArrayList<String>();
		for (Resume_ViewVO resumeView : dao.resumeViewSelectList(paging, compId)) {
			memIdList.add(resumeView.getMemId());
		}
		List<MemberVO> memberVO = new ArrayList<MemberVO>();
		if(! memIdList.isEmpty()) {
			memberVO = tDao.talentList(memIdList);
		}
		
		return memberVO;
	}

	@Override
	public ServiceResult resumeViewDelete(List<String> memId) {
		// TODO Auto-generated method stub
		return null;
	}


}
