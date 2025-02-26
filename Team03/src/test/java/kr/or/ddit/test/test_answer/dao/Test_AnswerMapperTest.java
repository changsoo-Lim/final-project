package kr.or.ddit.test.test_answer.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.annotation.RootContextWebConfig;
import kr.or.ddit.vo.Test_AnswerVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j 
@Transactional
@RootContextWebConfig
class Test_AnswerMapperTest {

	@Inject
	Test_AnswerMapper mapper;
	
	Test_AnswerVO testAnswer;
	
	@BeforeEach
    void BeforeEach() {
		testAnswer = new Test_AnswerVO();
		testAnswer.setItemNo(2);
		testAnswer.setCandidateNo(1);
		testAnswer.setAnswerContent("정답을적었어");
        assertEquals(1, mapper.insertTestAnswer(testAnswer));
    }
	
	@Test
	void testInsertTestAnswer() {
		testAnswer = new Test_AnswerVO();
		testAnswer.setItemNo(3);
		testAnswer.setCandidateNo(2);
		testAnswer.setAnswerContent("정답을적었어2");
        assertEquals(1, mapper.insertTestAnswer(testAnswer));
	}

	@Test
	void testSelectTestAnswerList() {
		int candidateNo = testAnswer.getCandidateNo();
		assertNotNull(mapper.selectTestAnswerList(candidateNo), "★★★★★★★★★★★★ 테스트 코드조회");
	}

}
