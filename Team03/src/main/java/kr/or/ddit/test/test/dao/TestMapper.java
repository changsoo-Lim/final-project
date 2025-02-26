package kr.or.ddit.test.test.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.TestVO;

@Mapper
public interface TestMapper {
	/**
	 * 테스트 유형 공통코드 리스트 조회
	 * @return List<CodeVO>
	 */
	public List<CodeVO> selectTestCode();
	
	/**
	 * 테스트 등록
	 * @param test TestVO
	 * @return 성공 1 : 실패 0
	 */
	public int insertTest(TestVO test);
	
	/**
	 * 유형별 테스트 리스트 조회
	 * @param testCd 시험코드(인성, 적성, 입사, 코딩테스트)
	 * @return List<TestVO> 
	 */
	public List<TestVO> selectTestList(String testCd);
	
	/**
	 * 테스트 상세조회
	 * @param testNo 시험번호
	 * @return TestVO
	 */
	public TestVO selectTest(int testNo);
	
	/**
	 * 테스트 수정
	 * @param test TestVO
	 * @return 성공 1 / 실패 0
	 */
	public int updateTest(TestVO test);
	
	/**
	 * 테스트 삭제여부 수정
	 * @param testNo 시험번호
	 * @return 성공 1 / 실패 0
	 */
	public int deleteTest(int testNo);
	
	
}
