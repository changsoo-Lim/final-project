package kr.or.ddit.member.member.service;

import java.util.List;

import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.MemberVO;

public interface MypageService {
	public MemberVO readMemberData(String memId);
	
	public List<CodeVO> readSalaryCodeList();
	
	public List<CodeVO> readWorkTypeCodeList();
	
	public List<CodeVO> readCompCodeList();
	
	public List<CodeVO> readClassifyCodeList();
	
	public List<CodeVO> readTotalApply(String memId);
	
	public List<CodeVO> readTotalPosition(String memId);
	
	public List<CodeVO> readTotalCandidate(String memId);
	
	public List<CodeVO> readTotalApplyOpen(String memId);
	
	public List<CodeVO> readTotalEmployScrap(String memId);
	
	public List<CodeVO> readTotalInterComp(String memId);
}
