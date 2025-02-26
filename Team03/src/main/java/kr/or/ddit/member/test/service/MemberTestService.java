package kr.or.ddit.member.test.service;

import java.util.List;

import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.test.candidate.vo.CandidateDTO;
import kr.or.ddit.vo.CandidateVO;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.Test_AnswerVO;

public interface MemberTestService {
	/**
	 * 검사/테스트 현황 리스트 조회
	 * @param paging PaginationInfo
	 * @param memId 회원아이디
	 * @return CandidateVO 응시자 관리 리스트
	 */
	public List<CandidateVO> readCandidateList(PaginationInfo paging, String memId);
	
	/**
	 * 테스트 공통코드 조회
	 * @return CodeVO 공통코드 리스트
	 */
	public List<CodeVO> readTestCode();
	
	/**
	 * 검사/테스트 현황 건수 조회
	 * @param paging PaginationInfo
	 * @param memId 회원아이디
	 * @return int 검사/테스트 건수
	 */
	public int readTotalCandidate(PaginationInfo paging, String memId);
	
	/**
	 * 검사/테스트 응시 단건 조회
	 * @param candidateNo 응시자 번호 
	 * @return CandidateVO 
	 */
	public CandidateDTO readCandidateDetail(int candidateNo);
	
	/**
	 * 테스트의 문제 수 조회
	 * @param testNo 테스트 번호
	 * @return int 문제 수 
	 */
	public int readTestQuestnCount(int testNo);
	
	/**
	 * 테스트 정답 처리
	 * @param answers 선택한 정답
	 * @param candidateNo 응시자번호
	 * @param testNo  시험번호
	 * @param testType 시험타입
	 */
	public void processTestAnswers(List<Test_AnswerVO> answers, Integer candidateNo, Integer testNo, String testType);
}
