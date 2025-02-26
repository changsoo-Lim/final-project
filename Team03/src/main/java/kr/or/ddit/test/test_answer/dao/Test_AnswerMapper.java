package kr.or.ddit.test.test_answer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.CandidateVO;
import kr.or.ddit.vo.Test_AnswerVO;
import kr.or.ddit.vo.Test_KeywordVO;

@Mapper
public interface Test_AnswerMapper {
	
	/**
	 * 테스트 정답 등록
	 * @param testAnswer Test_AnswerVO
	 * @return 성공 1 / 실패 0
	 */
	public int insertTestAnswer(Test_AnswerVO testAnswer);
	
	/**
	 * 테스트 정답 리스트 조회
	 * @param candidateNo 응시자번호
	 * @return Test_AnswerVO 리스트
	 */
	public List<Test_AnswerVO> selectTestAnswerList(int candidateNo);
	
	/**
	 * 객관식 정답 확인 (인성검사X)
	 * @param queNo 문제번호
	 * @return 정답 itemNo
	 */
	public int selectCorrectItem(int queNo);
	
	/**
	 * 주관식 키워드 확인
	 * @param itemNo 지문번호 
	 * @return List<Test_KeywordVO> 주관식 키워드 조회
	 */
	public List<Test_KeywordVO> selectKeywordsForItem(int itemNo);
	
	/**
	 * 점수 업데이트
	 * @param candidate candidateVO
	 * @return 성공 1 / 실패 0
	 */
	public int updateCandidateScore(CandidateVO candidate);
	
	/**
	 * 인성검사의 모든 선택지의 점수 조회
	 * @param itemNo 지문번호
	 * @return int 모든 선택지의 점수
	 */
	public int getItemScore(int itemNo);
}
