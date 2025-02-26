package kr.or.ddit.employscrap.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.EmployVO;
import kr.or.ddit.vo.EmployscrapVO;

@Mapper
public interface EmployScrapMapper {
	/**
	 * 해당 회원의 공고 스트랩 목록
	 * @param memId
	 * @return
	 */
	public List<EmployscrapVO> selectEmpScrap(@Param("memId") String memId, @Param("paging") PaginationInfo<EmployscrapVO> paging);
	/**
	 * 스크랩 여부 확인
	 * @param memId
	 * @param employNo
	 * @return
	 */
	public int selectEmpScrapCheck(@Param("memId") String memId, @Param("employNo") int employNo);
	/**
	 * 관심공고 등록
	 * @param empScrap
	 * @return
	 */
	public int insertEmpScrap(EmployscrapVO empScrap);
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
