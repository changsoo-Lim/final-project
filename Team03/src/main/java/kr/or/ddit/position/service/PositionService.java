package kr.or.ddit.position.service;

import java.util.List;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.EmployVO;
import kr.or.ddit.vo.PositionVO;

public interface PositionService {
	
	/**
	 * 포지션 제안 생성
	 * @return
	 */
	public ServiceResult insertPosition(PositionVO position);
	
	/**
	 * 이미 제안된 포지션인지 체크
	 * @return
	 */
	public ServiceResult checkPosition(PositionVO position);
	
	/**
	 * 회원에게 제안한 포지션 리스트 조회
	 * @param memId
	 * @return
	 */
	public List<PositionVO> selectMemberPositionList(String memId, PaginationInfo<PositionVO> pagination);
	
	public EmployVO readEmployNo(int filedNo);
	
	/**
	 * 이미 제안한 포지션인지 확인
	 * @param position
	 */
	ServiceResult selectPoistionCheck(PositionVO position);
	
	/**
	 * 기업 포지션 제안 리스트 
	 * @param username
	 * @param pagination
	 * @return
	 */
	public List<PositionVO> selectPositionList(String username, PaginationInfo<PositionVO> pagination);
	
}
