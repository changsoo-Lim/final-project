package kr.or.ddit.admin.board.service;

import java.util.List;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.File_DetailVO;

public interface AdminBoardService {
	
	/**
	 * 관리자 게시글 등록
	 * @param board
	 */
	public ServiceResult createAdminBoard(BoardVO board);
	/**
	 * 관리자 게시글 상세조회
	 * @param boardNo
	 * @return
	 */
	public BoardVO readAdminBoard(int boardNo);
	/**
	 * 관리자 게시글 목록 조회
	 * @param paginationInfo
	 * @return
	 */
	public List<BoardVO> readAdminBoardList(PaginationInfo paging);
	/**
	 * 관리자 게시글 수정
	 * @param board
	 */
	public ServiceResult modifyAdminBoard(BoardVO board);
	/**
	 * 관리자 게시글 삭제
	 * @param boardNo
	 */
	public ServiceResult removeAdminBoard(int boardNo);
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
	 * 관리자 게시글 선택(전체)삭제
	 * @param boardNo
	 * @return
	 */
	public ServiceResult removeAdminBoards(List<Integer> boardNo);
	
	/**
	 * 관리자 게시글 총 개수
	 * @param simpleCondition
	 * @return
	 */
	public int getAdminTotalCount(PaginationInfo paging);
}
