package kr.or.ddit.test.candidate.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.annotation.RootContextWebConfig;
import kr.or.ddit.vo.CandidateVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@RootContextWebConfig
class CandiDateMapperTest {
	
	@Inject
	CandiDateMapper mapper;
	
	CandidateVO candidate;
	
	@BeforeEach
    void BeforeEach() {
		candidate = new CandidateVO();
		candidate.setTestNo(3);
		candidate.setApplyNo(51);
		candidate.setCandidateSdt("20241231");
		candidate.setCandidateEdt("20250101");
        assertEquals(1, mapper.insertCandidate(candidate));
    }

	@Test
	void testInsertCandidate() {
		candidate = new CandidateVO();
		candidate.setTestNo(4);
		candidate.setApplyNo(52);
		candidate.setCandidateSdt("20250101");
		candidate.setCandidateEdt("20250102");
        assertEquals(1, mapper.insertCandidate(candidate));
	}

	@Test
	void testSelectCandidate() {
		assertNotNull(mapper.selectCandidate(22), "★★★★★★★★★★★★ 테스트 코드조회");
	}

	@Test
	void testUpdateCandidate() {
		candidate.setCandidateScore(100);
		assertEquals(1, mapper.updateCandidate(candidate));
	}

}
