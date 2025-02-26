package kr.or.ddit.company.talent.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.EmployVO;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.Resume_ViewVO;

public interface TalentService {
	/**
	 * 공통 코드 리스트 검색
	 * @return 공통코드 리스트
	 */
	public List<CodeVO> readCode();

	/**
	 * 인재 검색 리스트 검색
	 * @param paging (검색 조건, 페이지번호)
	 * @return 검색 조건에 필터링된 인재 리스트
	 */
	public List<MemberVO> searchTalent(PaginationInfo<MemberVO> paging);

	/**
	 * 인재 디테일 검색
	 * @param talentId (검색할 인재 아이디)
	 * @return 검색안 인재 MemberVO
	 */
	public MemberVO readMember(Resume_ViewVO resumeViewVO);
	
	/**
	 * 이력서 열람 인재 리스트
	 * @param memIdList
	 * @return
	 */
	public List<MemberVO> talentList(@Param("memIdList") List<String> memIdList);
	
	/**
	 * 기업 공고 리스트 조회
	 * @param compId
	 * @param talentId 
	 */
	public List<EmployVO> getEmploy(String compId, String talentId);
	
}
