package kr.or.ddit.admin.board.exception;

/**
 * 관리자 게시판 에서만 사용할 커스텀 예외
 *
 */
public class AdminBoardException extends RuntimeException {

	public AdminBoardException() {
		super();
	}
	
	public AdminBoardException(int boardNo) {
		super(String.format("글번호 %d 예서 예외 발생", boardNo));
		
	}

	public AdminBoardException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public AdminBoardException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public AdminBoardException(String message) {
		super(message);
		
	}

	public AdminBoardException(Throwable cause) {
		super(cause);
		
	}
}
