package kr.or.ddit.admin.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.paging.PaginationInfo;import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.vo.BoardVO;

@Mapper
public interface AdminBoardMapper {
	
	/**
	 * 관리자 게시글 등록
	 * @param board
	 * @return
	 */
	public Integer insertAdminBoard(BoardVO board);
	/**
	 * 관리자 게시글 상세조회
	 * @param boardNo
	 * @return
	 */
	public BoardVO selectAdminBoard(int boardNo);
	/**
	 * 관리자 게시글 목록 조회 
	 * 페이지네이션
	 * @param paginationInfo
	 * @return
	 */
	public List<BoardVO> selectAdminBoardList(PaginationInfo paging);
	/**
	 * 게시글 목록수 조회
	 * @param paginationInfo
	 * @return
	 */
	public int selectTotalRecord(PaginationInfo paging);
	/**
	 * 관리자 게시글 수정
	 * @param board
	 * @return
	 */
	public int updateAdminBoard(BoardVO board);
	/**
	 * 관리자 게시글 삭제
	 * @param boardNo
	 * @return
	 */
	public int deleteAdminBoard(int boardNo);
	
	/**
	 * 관리자 게시글 선택(전체)삭제
	 * @param boardNo
	 * @return
	 */
	public int deleteAdminBoards(List<Integer> boardNo);
	
	/**
	 * 관리자 게시글 총 개수
	 * @param condition
	 * @return
	 */
	public int getAdminTotalCount(PaginationInfo paging);
}
