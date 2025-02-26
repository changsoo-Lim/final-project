package kr.or.ddit.comment.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.board.dao.BoardMapper;
import kr.or.ddit.comment.dao.CommentMapper;
import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.notification.service.NotificationService;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.CommentVO;
import kr.or.ddit.vo.NotificationVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

	private final CommentMapper dao;
	
	@Inject
	private BoardMapper boardMapper;

	@Inject
	NotificationService notificationService;
	
	@Override
	public List<CommentVO> readCommentList(int boardNo) {
		return dao.selectCommentList(boardNo);
	}

	@Override
	public ServiceResult createComment(CommentVO comment) {
		if( dao.insertComment(comment) > 0) {
			
			BoardVO board = boardMapper.selectBoard(comment.getBoardNo());
			
			NotificationVO notification = new NotificationVO();
			notification.setMemId(board.getMemId()); // 알림을 받을 사용자 ID (게시글 작성자)
	        notification.setNotificType("COMMENT"); // 알림 유형
	        notification.setNotificUrl("/main/board/" + comment.getBoardNo()); // 알림 URL
	        notification.setNotificCont("[댓글 알림] 게시글에 댓글이 달렸습니다."); // 알림 내용으로 댓글 내용 일부 설정
	        
	        notificationService.notifyAndSave(notification);
			
			return ServiceResult.OK; 
		}else {
			return	ServiceResult.FAIL;
		}
	}

	@Override
	public ServiceResult modifyComment(CommentVO comment) {
		int updateCount = dao.updateComment(comment);
	    return updateCount > 0 ? ServiceResult.OK : ServiceResult.FAIL;
	}

	@Override
	public ServiceResult deleteComment(LocalDateTime commentDate) {
		if( dao.deleteComment(commentDate) > 0) {
			return ServiceResult.OK; 
		}else {
			return	ServiceResult.FAIL;
		}
	}

}
