package kr.or.ddit.test.test.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.annotation.RootContextWebConfig;
import kr.or.ddit.vo.TestVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@RootContextWebConfig
class TestMapperTest {
	
	@Inject
	TestMapper mapper;
	
	TestVO test;
	
	@BeforeEach
    void BeforeEach() {
        test = new TestVO();
        test.setCompId("client001");
		test.setTestCd("TE01");
		test.setTestNm("stackuptest");
		test.setTestTime(1800);
		assertEquals(1, mapper.insertTest(test));
    }
	
	@Test
	void testSelectTestCode() {
		assertNotNull(mapper.selectTestCode(), "★★★★★★★★★★★★ 테스트 코드조회");
	}

	@Test
	void testInsertTest() {
		TestVO insertTest = new TestVO();
		insertTest.setCompId("client001");
		insertTest.setTestCd("TE01");
		insertTest.setTestNm("stackuptest");
		insertTest.setTestTime(1800);
		assertEquals(1, mapper.insertTest(insertTest)); 
	}

	@Test
	void testSelectTestList() {
		String testCd = test.getTestCd();
		assertNotNull(mapper.selectTestList(testCd), "★★★★★★★★★★★★ 테스트 코드조회");
	}

	@Test
	void testSelectTest() {
		int testNo = test.getTestNo();
		assertNotNull(mapper.selectTest(testNo), "★★★★★★★★★★★★ 테스트 코드조회");
	}

	@Test
	void testUpdateTest() {
		int testNo = test.getTestNo();
		TestVO updateTest = mapper.selectTest(testNo);
		updateTest.setTestCd("TE02");
		updateTest.setTestNm("testtest");
		updateTest.setTestTime(1900);
		updateTest.setTestDel("Y");
		assertEquals(1, mapper.updateTest(updateTest));
	}

	@Test
	void testDeleteTest() {
		int testNo = test.getTestNo();
		assertEquals(1, mapper.deleteTest(testNo));
	}

}
