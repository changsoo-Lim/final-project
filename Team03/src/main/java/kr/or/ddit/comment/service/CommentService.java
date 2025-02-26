package kr.or.ddit.comment.service;

import java.time.LocalDateTime;
import java.util.List;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.vo.CommentVO;

public interface CommentService {
	
	/**
	 * 댓글 조회
	 * @return
	 */
	public List<CommentVO> readCommentList(int boardNo);
	/**
	 * 댓글 등록
	 * @param comment
	 */
	public ServiceResult createComment(CommentVO comment);
	/**
	 * 댓글 수정
	 * @param comment
	 */
	public ServiceResult modifyComment(CommentVO comment);
	/**
	 * 댓글 삭제
	 * @param commentDate
	 */
	public ServiceResult deleteComment(LocalDateTime commentDate);
}
