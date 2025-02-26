package kr.or.ddit.admin.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.annotation.RootContextWebConfig;
import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.test.test.vo.TestVOWrapper;
import kr.or.ddit.vo.TestItemVO;
import kr.or.ddit.vo.TestVO;
import kr.or.ddit.vo.Test_KeywordVO;
import kr.or.ddit.vo.Test_QuestnVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@RootContextWebConfig
class AdminTestServiceImplTest {
	
	
	@Inject
	AdminTestService testService;
	
	TestVOWrapper testVOWrapper;
	TestVO test;
	Test_QuestnVO testQuestnVO;
	TestItemVO testItemVO;
	
	Test_KeywordVO testKeywordVO;
	
	
	@BeforeEach
    void BeforeEach() {
		testVOWrapper = new TestVOWrapper();
    }
	
	@Test
	void testCreateTest() {
		test = new TestVO();
        test.setTestNm("인성검사");
        test.setTestCd("TE01");
        test.setCompId("admin001");
        test.setTestTime(1800);
        testVOWrapper.setTest(test);

        testQuestnVO = new Test_QuestnVO();
        testQuestnVO.setQueCont("문제 내용");
        testQuestnVO.setQueTurn(1);
        testQuestnVO.setAtchFileNo(9);

        testItemVO = new TestItemVO();
        testItemVO.setItemCont("객관식 지문 1");
        testItemVO.setItemScore(10);
        testItemVO.setItemYn("Y");
        testItemVO.setQueType("QT01");
        testItemVO.setAtchFileNo(9);
        testQuestnVO.setItemList(Arrays.asList(testItemVO));

        testVOWrapper.setTestQuestnList(Arrays.asList(testQuestnVO));
        
        assertEquals(ServiceResult.OK, testService.createTest(testVOWrapper));
	}

	@Test
	void testModifyTest() {
	    test = new TestVO();
	    test.setTestNo(1);
	    test.setTestNm("수정된 테스트");
	    test.setTestCd("TE02");
	    testVOWrapper.setTest(test);

	    testQuestnVO = new Test_QuestnVO();
	    testQuestnVO.setQueNo(1);
	    testQuestnVO.setQueCont("수정된 문제 내용");
	    testQuestnVO.setQueTurn(2);
	    testQuestnVO.setAtchFileNo(9);

	    testItemVO = new TestItemVO();
	    testItemVO.setItemNo(1);
	    testItemVO.setItemCont("수정된 지문 내용");
	    testItemVO.setItemYn("N");
	    testQuestnVO.setItemList(Arrays.asList(testItemVO));

	    testKeywordVO = new Test_KeywordVO();
	    testKeywordVO.setKeywdNo(1);
	    testKeywordVO.setKeywdCont("수정된 키워드");
	    testKeywordVO.setKeywdScore(10);
	    testKeywordVO.setItemNo(1);
	    testItemVO.setKeywordList(Arrays.asList(testKeywordVO));

	    testVOWrapper.setTestQuestnList(Arrays.asList(testQuestnVO));

	    assertEquals(ServiceResult.OK, testService.modifyTest(testVOWrapper));
	}
}
