package kr.or.ddit.member.member.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.MemberVO;

@Mapper
public interface MypageMapper {
	
	public MemberVO selectMemberData(String memId);
	
	public List<CodeVO> selectSalaryCodeList();
	
	public List<CodeVO> selectWorkTypeCodeList();
	
	public List<CodeVO> selectCompCodeList();
	
	public List<CodeVO> selectClassifyCodeList();
	
	public List<CodeVO> selectTotalApply(String memId);
	
	public List<CodeVO> selectTotalPosition(String memId);
	
	public List<CodeVO> selectTotalCandidate(String memId);
	
	public List<CodeVO> selectTotalApplyOpen(String memId);
	
	public List<CodeVO> selectTotalEmployScrap(String memId);
	
	public List<CodeVO> selectTotalInterComp(String memId);
}
