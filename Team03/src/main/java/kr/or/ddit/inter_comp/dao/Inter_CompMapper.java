package kr.or.ddit.inter_comp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.Inter_CompVO;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.Work_CityVO;
import kr.or.ddit.vo.Work_CondVO;

@Mapper
public interface Inter_CompMapper {
	/**
	 * 해당 회원의 기업 스트랩 목록
	 * 
	 * @param memId
	 * @return
	 */
	public List<Inter_CompVO> selectInterComp(String memId);

	/**
	 * 스크랩 여부 확인
	 * 
	 * @param memId
	 * @param employNo
	 * @return
	 */
	public int selectInterCompCheck(@Param("memId") String memId, @Param("compId") String compId);

	/**
	 * 관심기업 등록
	 * 
	 * @param InterComp
	 * @return
	 */
	public int insertInterComp(Inter_CompVO InterComp);

	/**
	 * 관심기업 삭제 여부 수정
	 * 
	 * @param InterComp
	 * @return
	 */
	public int updateScrapStatus(Inter_CompVO InterComp);

	/**
	 * 기업의 관심기업 등록 수 합계
	 * 
	 * @param employNo
	 * @return
	 */
	public int totalInterComp(String compId);
	
	/**
	 * 기업을 관심 등록한 회원 리스트 조회
	 * @param username
	 * @param pagination 
	 * @return
	 */
	public List<Inter_CompVO> selectMemList(@Param("compId") String username,@Param("paging") PaginationInfo<Inter_CompVO> pagination);
	
	/**
	 * 페이징 토탈 레코드
	 * @param username
	 * @return
	 */
	public int selectTotalRecorde(String username);
	
	public List<MemberVO> memList();
	
	public List<Work_CondVO> workCondList(String memId);
	
	public List<Work_CityVO> workCityList(Integer integer);
	
	
	
}
