package kr.or.ddit.admin.resource.service;

import java.util.List;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.vo.File_DetailVO;
import kr.or.ddit.vo.ResourceVO;

public interface AdminResourceService {
	
	/**
	 * 관리자 자료 등록
	 * @param resource
	 * @return
	 */
	public ServiceResult createAdminResource(ResourceVO resource);
	/**
	 * 관리자 자료 목록 상세조회
	 * @param resourceNo
	 * @return
	 */
	public ResourceVO readAdminResource(int resourceNo);
	/**
	 * 관리자 자료실 목록 조회
	 * @param paging
	 * @return
	 */
	public List<ResourceVO> readAdminResourceList(PaginationInfo paging);
	/**
	 * 관리자 자료 수정
	 * @param resource
	 * @return
	 */
	public ServiceResult modifyAdminResource(ResourceVO resource);
	/**
	 * 관리자 자료 삭제
	 * @param resourceNo
	 * @return
	 */
	public ServiceResult removeAdminResource(int resourceNo);
	/**
	 * 파일 다운로드
	 * @param atchFileNo
	 * @param fileSn
	 * @return
	 */
	public File_DetailVO download(int atchFileNo, int fileSn);
	/**
	 * 파일 한건 삭제
	 * @param atchFileNo
	 * @param fileSn
	 */
	public void removeFile(int atchFileNo, int fileSn);
	
	/**
	 * 게시글 목록 총 개수
	 * @param simpleCondition
	 * @return
	 */
	public int getTotalCount(SimpleCondition simpleCondition);
}
