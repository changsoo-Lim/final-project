package kr.or.ddit.company.talent.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.EmployVO;
import kr.or.ddit.vo.MemberVO;

@Mapper
public interface TalentMapper {
	
	/**
	 * 기업 리스트 조회
	 * @return
	 */
	public List<EmployVO> getEmploy();

	/**
	 * 공통코드 리스트
	 * @return 검색한 공통코드 리스트
	 */
	public List<CodeVO> selectCode();
	
	/**
	 * 검색한 인재 리스트
	 * @param paging ( 검색조건 , 페이지 번호)
	 * @return 필터링된 인재 리스트
	 */
	public List<MemberVO> searchTalent(PaginationInfo<MemberVO> paging);
	
	/**
	 * 페이지 네이션에 사용할 페이지 토탈 번호
	 * @param paging (검색할 조건)
	 * @return 검색된 토탈 번호
	 */
	public int selectTotalRecord(PaginationInfo<MemberVO> paging);

	/**
	 * 인재 디테일 검색
	 * @param talentId (검색할 인재 아이디)
	 * @return 검색된 인재 MemberVO
	 */
	public MemberVO selectMember(String talentId);
	
	/**
	 * 이력서 열람 내역 인재 리스트
	 * @param memIdList
	 * @return
	 */
	public List<MemberVO> talentList(@Param("memIdList") List<String> memIdList);
	
	/**
	 * 인재 포지션 제안 기업 공고 리스트 출력
	 * @param compId
	 * @param talentId 
	 * @return
	 */
	public List<EmployVO> selectEmployList(@Param("compId") String compId,@Param("memId") String talentId);

}
