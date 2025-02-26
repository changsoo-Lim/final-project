package kr.or.ddit.member.myapply.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.ApplyVO;
import kr.or.ddit.vo.CodeVO;

@Mapper
public interface MyApplyMapper {
	
	/**
	 * 나의 지원 현황 리스트
	 * @param paging
	 * @return
	 */
	public List<ApplyVO> selectMyApplyList(@Param("memId") String memId, @Param("paging") PaginationInfo paging);
	
	/**
	 * 페이징 처리를 위한 검색 결과 레코드 수 조회
	 * @param paging
	 * @return
	 */
	public int selectTotalRecord(@Param("memId") String memId, @Param("paging") PaginationInfo paging);
	
	/**
	 * 게시글 목록 총 개수 
	 * @param condition
	 * @return
	 */
	public int getTotalCount(@Param("memId") String memId, @Param("paging") PaginationInfo paging);
	
	
	public List<CodeVO> selectApplyCodeList();

}
