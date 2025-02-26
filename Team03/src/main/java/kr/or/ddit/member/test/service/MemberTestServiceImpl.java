package kr.or.ddit.member.test.service;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.member.test.dao.MemberTestMapper;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.test.candidate.vo.CandidateDTO;
import kr.or.ddit.test.test_answer.dao.Test_AnswerMapper;
import kr.or.ddit.vo.CandidateVO;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.Test_AnswerVO;
import kr.or.ddit.vo.Test_KeywordVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberTestServiceImpl implements MemberTestService {
	
	@Inject
	MemberTestMapper memberTestMapper;
	
	@Inject
	Test_AnswerMapper testAnswerMapper;

	@Override
	public List<CandidateVO> readCandidateList(PaginationInfo paging, String memId) {
		if(paging!=null) {
			int totalRecord = memberTestMapper.selectTotalCandidate(paging, memId);
			paging.setTotalRecord(totalRecord);
		}
		
		List<CandidateVO> candidateList = memberTestMapper.selectCandidateList(paging, memId);
		
//		if (candidateList == null) {
//	        throw new IllegalStateException("테스트 리스트가 없습니다.");
//	    }
		return candidateList;
	}

	@Override
	public List<CodeVO> readTestCode() {
		List<CodeVO> testCode = memberTestMapper.selectTestCode();
//		if (testCode == null) {
//	        throw new IllegalStateException("해당 테스트 코드가 없습니다.");
//	    }
		return testCode;
	}

	@Override
	public int readTotalCandidate(PaginationInfo paging, String memId) {
		int testCode = memberTestMapper.selectTotalCandidate(paging, memId);
//		if (!(testCode > 0)) {
//	        throw new IllegalStateException("해당 테스트 코드가 없습니다.");
//	    }
		return testCode;
	}

	@Override
	public CandidateDTO readCandidateDetail(int candidateNo) {
		CandidateDTO candidate = memberTestMapper.selectCandidateDetail(candidateNo);
//		if (candidate == null) {
//	        throw new IllegalStateException("해당 테스트 코드가 없습니다.");
//	    }
		return candidate;
	}

	@Override
	public int readTestQuestnCount(int testNo) {
		int testQuestnCount = memberTestMapper.selectTestQuestnCount(testNo);
//		if (!(testQuestnCount > 0)) {
//	        throw new IllegalStateException("해당 테스트 코드가 없습니다.");
//	    }
		return testQuestnCount;
	}

	@Override
	public void processTestAnswers(List<Test_AnswerVO> answers, Integer candidateNo, Integer testNo, String testType) {
		int totalScore = 0;

	    for (Test_AnswerVO answer : answers) {
	        // 답안 저장
	        testAnswerMapper.insertTestAnswer(answer);

	        if ("TE01".equals(testType)) {
	            // 인성검사 유형: 선택지 점수를 누적
	            Integer itemScore = testAnswerMapper.getItemScore(answer.getItemNo());
	            log.info("itemScore : "+itemScore.toString());
	            totalScore += Optional.ofNullable(itemScore).orElse(0);

	        } else if (answer.getAnswerContent() == null) {
	            // 객관식: itemNo를 정답으로 확인
	            Integer correctItemNo = testAnswerMapper.selectCorrectItem(answer.getItemNo());
	            if (correctItemNo != null && correctItemNo.equals(answer.getItemNo())) {
	                // 정답 점수 가져오기
	                Integer itemScore = testAnswerMapper.getItemScore(answer.getItemNo());
	                totalScore += Optional.ofNullable(itemScore).orElse(0);
	            }
	        } else {
	            // 주관식: 키워드와 비교하여 점수 계산
	            List<Test_KeywordVO> keywords = testAnswerMapper.selectKeywordsForItem(answer.getItemNo());
	            String[] userAnswers = answer.getAnswerContent().split(" ");
	            int questionScore = 0;

	            for (Test_KeywordVO keyword : keywords) {
	                for (String userAnswer : userAnswers) {
	                    if (userAnswer.equalsIgnoreCase(keyword.getKeywdCont())) {
	                        questionScore += keyword.getKeywdScore();
	                        break;
	                    }
	                }
	            }
	            totalScore += questionScore;
	        }
	    }

	    // 최종 점수 업데이트
	    CandidateVO candidate = new CandidateVO();
	    candidate.setCandidateNo(candidateNo);
	    candidate.setTestNo(testNo);
	    candidate.setCandidateScore(totalScore);
	    testAnswerMapper.updateCandidateScore(candidate);
	}
}
