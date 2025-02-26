package kr.or.ddit.test.test_keyword.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.Test_KeywordVO;

@Mapper
public interface Test_keywordMapper {
	
	/**
	 * 서술형 키워드정답 등록
	 * @param testKeyword Test_KeywordVO
	 * @return 성공 1 / 실패 0
	 */
	public int insertTestKeyword(Test_KeywordVO testKeyword);
	
	/**
	 * 지문의 서술형 키워드정답 리스트 조회
	 * @param itemNo 지문번호
	 * @return Test_KeywordVO 리스트 
	 */
	public List<Test_KeywordVO> selectTestKeywordList(int itemNo);
	
	/**
	 * 서술형 키워드정답 수정
	 * @param testKeyword Test_KeywordVO
	 * @return 성공 1 / 실패 0
	 */
	public int updateTestKeyword(Test_KeywordVO testKeyword);
	
	/**
	 * 테스트 지문 삭제여부 수정
	 * @param itemNo 지문번호
	 * @return 성공 1 / 실패 0
	 */
	public int deleteTestKeyword(int keywdNo);
}
