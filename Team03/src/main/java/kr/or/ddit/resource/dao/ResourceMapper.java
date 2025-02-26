package kr.or.ddit.resource.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.vo.ResourceVO;

@Mapper
public interface ResourceMapper {
	
	/**
	 * 관리자 자료 목록 상세조회
	 * @param resourceNo
	 * @return
	 */
	public ResourceVO selectResource(int resourceNo);
	/**
	 * 관리자 자료 목록 조회
	 * @return
	 */
	public List<ResourceVO> selectResourceList(PaginationInfo paging);
	/**
	 * 페이징 처리를 위한 검색 결과 레코드 수 조회
	 * @param paging
	 * @return
	 */
	public int selectTotalRecord(PaginationInfo paging);
	/**
	 * 게시글 목록 총 개수 
	 * @param condition
	 * @return
	 */
	public int getTotalCount(PaginationInfo paging);
}
