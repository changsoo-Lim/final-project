package kr.or.ddit.test.test_item.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.TestItemVO;

@Mapper
public interface Test_ItemMapper {
	/**
	 * 테스트 문제 유형 공통코드 리스트 조회
	 * @return List<CodeVO>
	 */
	public List<CodeVO> selectQustionTypeCode();
	
	/**
	 * 테스트 지문 등록
	 * @param testItem TestItemVO
	 * @return 성공 1 / 실패 0
	 */
	public int insertTestItem(TestItemVO testItem);
	
	/**
	 * 문제의 지문 리스트 조회
	 * @param queNo 문제번호
	 * @return TestItemVO 리스트
	 */
	public List<TestItemVO> selectTestItemList(int queNo);
	
	/**
	 * 문제의 지문 단건 조회
	 * @param itemNo 지문번호
	 * @return TestItemVO 단건 조회
	 */
	public TestItemVO selectTestItem(int itemNo);
	
	/**
	 * 테스트 지문 수정
	 * @param testItem TestItemVO
	 * @return 성공 1 / 실패 0
	 */
	public int updateTestItem(TestItemVO testItem);
	
	/**
	 * 테스트 지문 삭제여부 수정
	 * @param itemNo 지문번호
	 * @return 성공 1 / 실패 0
	 */
	public int deleteTestItem(int itemNo);
}
