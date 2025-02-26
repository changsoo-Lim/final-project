package kr.or.ddit.member.member.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.member.member.dao.MypageMapper;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.MemberVO;

@Service
public class MypageServiceImpl implements MypageService {

	@Inject
	MypageMapper mapper;
	
	@Override
	public MemberVO readMemberData(String memId) {
		return mapper.selectMemberData(memId);
	}

	@Override
	public List<CodeVO> readSalaryCodeList() {
		return mapper.selectSalaryCodeList();
	}

	@Override
	public List<CodeVO> readWorkTypeCodeList() {
		return mapper.selectWorkTypeCodeList();
	}

	@Override
	public List<CodeVO> readCompCodeList() {
		return mapper.selectCompCodeList();
	}

	@Override
	public List<CodeVO> readClassifyCodeList() {
		return mapper.selectClassifyCodeList();
	}

	@Override
	public List<CodeVO> readTotalApply(String memId) {
		return mapper.selectTotalApply(memId);
	}
	
	@Override
	public List<CodeVO> readTotalPosition(String memId) {
		return mapper.selectTotalPosition(memId);
	}

	@Override
	public List<CodeVO> readTotalCandidate(String memId) {
		return mapper.selectTotalCandidate(memId);
	}

	@Override
	public List<CodeVO> readTotalApplyOpen(String memId) {
		return mapper.selectTotalApplyOpen(memId);
	}

	@Override
	public List<CodeVO> readTotalEmployScrap(String memId) {
		return mapper.selectTotalEmployScrap(memId);
	}

	@Override
	public List<CodeVO> readTotalInterComp(String memId) {
		return mapper.selectTotalInterComp(memId);
	}

}
