package kr.or.ddit.inter_comp.service;

import java.util.List;

import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.Inter_CompVO;
import kr.or.ddit.vo.MemberVO;

public interface InterCompService {
	/**
	 * 해당 회원의 기업 스트랩 목록
	 * @param memId
	 * @return
	 */
	public List<Inter_CompVO> readInterComp(String memId);
	/**
	 * 관심기업 삭제 여부 수정
	 * @param empScrap
	 * @return
	 */
	public int updateScrapStatus(Inter_CompVO interComp);
	/**
	 * 기업의 관심기업 등록 수 합계
	 * @param employNo
	 * @return
	 */
	public int totalInterComp(String compId);
	
	/**
	 * 기업을 관심등록한 회원 리스트 조회
	 * @param username
	 * @param pagination 
	 * @return
	 */
	public List<Inter_CompVO> selectMemList(String username, PaginationInfo<Inter_CompVO> pagination);
}
