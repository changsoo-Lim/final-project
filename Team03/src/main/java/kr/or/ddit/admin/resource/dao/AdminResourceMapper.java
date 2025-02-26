package kr.or.ddit.admin.resource.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.vo.ResourceVO;

@Mapper
public interface AdminResourceMapper {
	
	/**
	 * 관리자 자료 등록
	 * @param resource
	 * @return
	 */
	public int insertAdminResource(ResourceVO resource);
	/**
	 * 관리자 자료 목록 상세조회
	 * @param resourceNo
	 * @return
	 */
	public ResourceVO selectAdminResource(int resourceNo);
	/**
	 * 관리자 자료 목록 조회
	 * @return
	 */
	public List<ResourceVO> selectAdminResourceList(PaginationInfo paging);
	/**
	 * 페이징 처리를 위한 검색 결과 레코드 수 조회
	 * @param paging
	 * @return
	 */
	public int selectTotalRecord(PaginationInfo paging);
	/**
	 * 관리자 자료 수정
	 * @param resource
	 * @return
	 */
	public int  updateAdminResource(ResourceVO resource);
	/**
	 * 관리자 자료 삭제
	 * @param resourceNo
	 * @return
	 */
	public int deleteAdminResource(int resourceNo);
	
	/**
	 * 게시글 목록 총 개수 
	 * @param condition
	 * @return
	 */
	public int getTotalCount(SimpleCondition condition);
}
