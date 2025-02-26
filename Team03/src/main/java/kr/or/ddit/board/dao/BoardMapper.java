package kr.or.ddit.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.vo.BoardVO;

@Mapper
public interface BoardMapper {
	
	/**
	 * 게시글 등록
	 * @param board
	 * @return
	 */
	public int insertBoard(BoardVO board);
	/**
	 * 게시글 상세조회
	 * @param boardNo 조회할 게시글 번호
	 * @return
	 */
	public BoardVO selectBoard(int boardNo);
	/**
	 * 게시글 목록 조회
	 * @param paging
	 * @return
	 */
	public List<BoardVO> selectBoardList(PaginationInfo paging);
	/**
	 * 페이징 처리를 위한 검색 결과 레코드 수 조회
	 * @param paging
	 * @return
	 */
	public int selectTotalRecord(PaginationInfo paging);
	/**
	 * 게시글 수정
	 * @param board
	 * @return
	 */
	public int updateBoard(BoardVO board);
	/**
	 * 게시글 삭제
	 * @param board
	 * @return
	 */
	public int deleteBoard(int boardNo);
	
	/**
	 * 게시글 목록 총 개수 
	 * @param condition
	 * @return
	 */
	public int getTotalCount(PaginationInfo paging);
}
