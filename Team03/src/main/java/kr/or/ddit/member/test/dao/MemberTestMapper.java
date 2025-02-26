package kr.or.ddit.member.test.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.test.candidate.vo.CandidateDTO;
import kr.or.ddit.vo.CandidateVO;
import kr.or.ddit.vo.CodeVO;

@Mapper
public interface MemberTestMapper {
	
	/**
	 * 검사/테스트 응시 현황 리스트 조회
	 * @param paging PaginationInfo
	 * @param memId 회원아이디
	 * @return CandidateVO 응시자 관리 리스트
	 */
	public List<CandidateVO> selectCandidateList(@Param("paging") PaginationInfo paging, @Param("memId") String memId);
	
	/**
	 * 테스트 공통코드 조회
	 * @return CodeVO 공통코드 리스트
	 */
	public List<CodeVO> selectTestCode();
	
	/**
	 * 검사/테스트 응시 현황 건수 조회
	 * @param paging PaginationInfo
	 * @param memId 회원아이디
	 * @return int 검사/테스트 건수
	 */
	public int selectTotalCandidate(@Param("paging") PaginationInfo paging, @Param("memId") String memId);
	
	/**
	 * 검사/테스트 응시 단건 조회
	 * @param candidateNo 응시자 번호 
	 * @return CandidateDTO 
	 */
	public CandidateDTO selectCandidateDetail(int candidateNo);
	
	/**
	 * 테스트의 문제 수 조회
	 * @param testNo 테스트 번호
	 * @return int 문제 수 
	 */
	public int selectTestQuestnCount(int testNo);
}
