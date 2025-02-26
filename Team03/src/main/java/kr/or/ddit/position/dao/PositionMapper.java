package kr.or.ddit.position.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.EmployVO;
import kr.or.ddit.vo.Inter_CompVO;
import kr.or.ddit.vo.PositionVO;

@Mapper
public interface PositionMapper {

	
	public PositionVO selectPosition(String PositionId);
	
	/**
	 * 포지션 상태코드변경(OF02:수락, OF03:거절, OF04:제안취소)
	 * @param positionVO
	 * @return
	 */
	public int updatePosition(PositionVO positionVO);
	
	public int deletePosition(String PositionId);
	
	/**
	 * 이미 제안한 포지션인지 확인 
	 * @param position
	 * @return
	 */
	public int checkPosition(PositionVO position);
	
	/**
	 * 포지션 제안 생성
	 * @param position
	 * @return
	 */
	public int insertPosition(PositionVO position);

	/**
	 * 회원 에게 제안한 포지션 리스트 조회
	 * @param memId
	 * @return
	 */
	public List<PositionVO> selectMemberPositionList(@Param("memId") String memId, @Param("paging") PaginationInfo<PositionVO> pagination);
	
	public EmployVO selectEmployNo(int filedNo);
	
	/**
	 * 이미 제안한 포지현 확인
	 * @param position
	 * @return
	 */
	public int selectPoistionCheck(PositionVO position);
	
	/**
	 * 토탈 레코드 조회
	 * @param username
	 * @return
	 */
	public int selectTotalRecorde(String username);
	
	/**
	 * 기업 포지션 리스트 조회
	 * @param username
	 * @param pagination
	 * @return
	 */
	public List<PositionVO> selectPositionList(@Param("compId") String username,@Param("paging") PaginationInfo<PositionVO> pagination);
}
