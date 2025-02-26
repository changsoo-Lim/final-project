package kr.or.ddit.test.test_question.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.Test_QuestnVO;

@Mapper
public interface Test_QuestionMapper {
	
	/**
	 * 테스트 문제 등록
	 * @param testQuestn 테스트 문제 VO
	 * @return 성공 1 / 실패 0
	 */
	public int insertTestQuestion(Test_QuestnVO testQuestn);
	
	/**
	 * 테스트의 문제 리스트 조회
	 * @param testNo 시험번호
	 * @return Test_QuestnVO 리스트 조회
	 */
	public List<Test_QuestnVO> selectTestQuestionList(int testNo);
	
	/**
	 * 테스트의 문제 조회
	 * @param testNo 시험번호
	 * @return Test_QuestnVO 테스트 단건 조회
	 */
	public Test_QuestnVO selectTestQuestion(int testNo);
	
	/**
	 * 테스트 문제 수정
	 * @param testQuestn 테스트 문제 VO
	 * @return 성공 1 / 실패 0
	 */
	public int updateTestQuestion(Test_QuestnVO testQuestn);
	
	/**
	 * 테스트 문제 삭제여부 수정
	 * @param testNo 시험번호
	 * @return 성공 1 / 실패 0
	 */
	public int deleteTestQuestion(int testNo);
}
