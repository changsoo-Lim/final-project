package kr.or.ddit.resource.service;

import java.util.List;

import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.vo.File_DetailVO;
import kr.or.ddit.vo.ResourceVO;

public interface ResourceService {
	
	/**
	 * 자료 목록 상세조회
	 * @param resourceNo
	 * @return
	 */
	public ResourceVO readResource(int resourceNo);
	/**
	 * 자료실 목록 조회
	 * @param paging
	 * @return
	 */
	public List<ResourceVO> readResourceList(PaginationInfo paging);
	/**
	 * 파일 다운로드
	 * @param atchFileNo
	 * @param fileSn
	 * @return
	 */
	public File_DetailVO download(int atchFileNo, int fileSn);
	/**
	 * 게시글 목록 총 개수
	 * @param simpleCondition
	 * @return
	 */
	public int getTotalCount(PaginationInfo paging);
	
}
