package kr.or.ddit.employscrap.service;

import java.util.List;

import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.EmployVO;
import kr.or.ddit.vo.EmployscrapVO;

public interface EmployScrapService {
	/**
	 * 해당 회원의 공고 스트랩 목록
	 * @param memId
	 * @return
	 */
	public List<EmployVO> readEmpScrap(String memId, PaginationInfo<EmployscrapVO> paging);
	/**
	 * 관심공고 삭제 여부 수정
	 * @param empScrap
	 * @return
	 */
	public int updateScrapStatus(EmployscrapVO empScrap);
	/**
	 * 공고의 관심공고 등록 수 합계
	 * @param employNo
	 * @return
	 */
	public int totalEmpScrap(int employNo);
}
