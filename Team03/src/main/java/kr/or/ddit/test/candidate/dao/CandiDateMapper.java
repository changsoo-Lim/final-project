package kr.or.ddit.test.candidate.dao;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.CandidateVO;

@Mapper
public interface CandiDateMapper {
	
	/**
	 * 응시자 등록
	 * @param candidate CandidateVO
	 * @return 성공 1 / 실패 0
	 */
	public int insertCandidate(CandidateVO candidate);
	
	/**
	 * 응시자 단일 조회
	 * @param applyNo 공고지원자번호
	 * @return
	 */
	public CandidateVO selectCandidate(int applyNo);
	
	/**
	 * 응시자 수정
	 * @param candidate CandidateVO
	 * @return 성공 1 / 실패 0
	 */
	public int updateCandidate(CandidateVO candidate);
}
