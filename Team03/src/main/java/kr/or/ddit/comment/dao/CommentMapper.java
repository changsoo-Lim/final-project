package kr.or.ddit.comment.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.CommentVO;

@Mapper
public interface CommentMapper {
	
	/**
	 * 댓글 리스트
	 * @return
	 */
	public List<CommentVO> selectCommentList(int boardNo);
	/**
	 * 댓글 등록
	 * @param comment
	 * @return
	 */
	public int insertComment(CommentVO comment);
	/**
	 * 댓글 수정
	 * @param comment
	 * @return
	 */
	public int updateComment(CommentVO comment);
	/**
	 * 댓글 삭제
	 * @param commentDate 댓글 작성 일시
	 * @return
	 */
	public int deleteComment(LocalDateTime commentDate);
	
}
