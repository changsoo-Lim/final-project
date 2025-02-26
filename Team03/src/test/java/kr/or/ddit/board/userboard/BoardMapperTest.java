package kr.or.ddit.board.userboard;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.annotation.RootContextWebConfig;
import kr.or.ddit.board.dao.BoardMapper;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.BoardVO;

@RootContextWebConfig
@Transactional
class BoardMapperTest {
	
	@Autowired
	BoardMapper mapper;
	
	BoardVO board;
	
	@BeforeEach
	void beforeEach() {
		board = new BoardVO();
		board.setBoardNo(1);
		board.setMemId("emp002");
		board.setBoardTitle("test");
		board.setBoardCont("test");
		board.setAtchFileNo(1);
		assertEquals(1, mapper.insertBoard(board));
	}
	
	@Test
	void testSelectBoard() {
		assertNotNull(mapper.selectBoard(board.getBoardNo()));
	}

	@Test
	void testSelectBoardList() {
		PaginationInfo paging = new PaginationInfo();
		paging.setCurrentPage(1);
		assertDoesNotThrow(() -> mapper.selectBoardList(paging));
	}

	@Test
	void testUpdateBoard() {
		board.setBoardTitle("test");
		board.setBoardCont("test");
		assertEquals(1, mapper.updateBoard(board));
	}

	@Test
	void testDeleteBoard() {
		assertEquals(1, mapper.deleteBoard(1));
	}

}
