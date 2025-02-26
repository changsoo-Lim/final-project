package kr.or.ddit.test.test_keyword.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.annotation.RootContextWebConfig;
import kr.or.ddit.vo.Test_KeywordVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@RootContextWebConfig
class Test_keywordMapperTest {
	
	@Inject
	Test_keywordMapper mapper;
	
	Test_KeywordVO testKeyword;
	
	@BeforeEach
    void BeforeEach() {
		testKeyword = new Test_KeywordVO();
		testKeyword.setKeywdCont("static");
		testKeyword.setKeywdScore(20);
		testKeyword.setItemNo(22);
        assertEquals(1, mapper.insertTestKeyword(testKeyword));
    }

	@Test
	void testInsertTestKeyword() {
		testKeyword = new Test_KeywordVO();
		testKeyword.setKeywdCont("final");
		testKeyword.setKeywdScore(21);
		testKeyword.setItemNo(23);
        assertEquals(1, mapper.insertTestKeyword(testKeyword));
	}

	@Test
	void testSelectTestKeywordList() {
		int itemNo = testKeyword.getItemNo();
		assertNotNull(mapper.selectTestKeywordList(itemNo), "★★★★★★★★★★★★ 테스트 코드조회");
	}

	@Test
	void testUpdateTestKeyword() {
		testKeyword.setKeywdCont("testestupdate");
		testKeyword.setKeywdScore(22);
		testKeyword.setItemNo(24);
		testKeyword.setKeywdDel("Y");
		assertEquals(1, mapper.updateTestKeyword(testKeyword));
	}

	@Test
	void testDeleteTestKeyword() {
		int keywdNo = testKeyword.getKeywdNo();
		assertEquals(1, mapper.deleteTestKeyword(keywdNo));
	}

}
