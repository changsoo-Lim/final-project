package kr.or.ddit.board.service;

import java.util.List;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.commons.exception.PKNotFoundException;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.File_DetailVO;

public interface BoardService {

	/**
	 * 게시글 생성
	 * @param board
	 * @return
	 */
	public ServiceResult createBoard(BoardVO board);
	/**
	 * 게시글 상세조회
	 * @param boardNo
	 * @return
	 * @throws PKNotFoundException 존재하지 않는 경우, 발생할 예외
	 */
	public BoardVO readBoard(int boardNo) throws PKNotFoundException;
	/**
	 * 게시글 조회
	 * @param paging
	 * @return
	 */
	public List<BoardVO> readBoardList(PaginationInfo paging);
	/**
	 * 게시글 수정
	 * @param board
	 * @return
	 */
	public ServiceResult modifyBoard(BoardVO board);
	/**
	 * 게시글 삭제
	 * @param boardNo
	 * @return
	 */
	public ServiceResult removeBoard(int boardNo);
	
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
	public int getTotalCount(PaginationInfo paging);
}
