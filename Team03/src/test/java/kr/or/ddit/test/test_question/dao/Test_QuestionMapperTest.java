package kr.or.ddit.test.test_question.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.annotation.RootContextWebConfig;
import kr.or.ddit.vo.Test_QuestnVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@RootContextWebConfig
class Test_QuestionMapperTest {

	@Inject
	Test_QuestionMapper mapper;
	
	Test_QuestnVO testQuestn;
	
	@BeforeEach
    void BeforeEach() {
        testQuestn = new Test_QuestnVO();
        testQuestn.setQueCont("testest");
        testQuestn.setQueTurn(1);
        testQuestn.setTestNo(3);
        testQuestn.setAtchFileNo(9);
        assertEquals(1, mapper.insertTestQuestion(testQuestn));
    }
	
	@Test
	void testInsertTestQuestion() {
		testQuestn.setQueCont("testestest");
		testQuestn.setQueTurn(2);
		testQuestn.setTestNo(3);
		testQuestn.setAtchFileNo(9);
		assertEquals(1, mapper.insertTestQuestion(testQuestn));
	}

	@Test
	void testSelectTestQuestionList() {
		int testNo = testQuestn.getQueNo();
		assertNotNull(mapper.selectTestQuestionList(testNo), "★★★★★★★★★★★★ 테스트 코드조회");
	}

	@Test
	void testUpdateTestQuestion() {
		testQuestn.setQueCont("testestestest");
		testQuestn.setQueTurn(3);
		testQuestn.setAtchFileNo(9);
		testQuestn.setQueDel("Y");
		assertEquals(1, mapper.updateTestQuestion(testQuestn));
	}

	@Test
	void testDeleteTestQuestion() {
		int queNo = testQuestn.getQueNo();
		assertEquals(1, mapper.deleteTestQuestion(queNo));
	}

}
