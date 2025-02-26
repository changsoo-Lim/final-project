package kr.or.ddit.company.test.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.test.test.vo.TestVOWrapper;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.TestVO;

public interface CompTestService {
	/**
	 * 테스트 유형 공통코드 리스트 조회
	 * @return List<CodeVO>
	 */
	public List<CodeVO> readTestCode();
	
	/**
	 * 테스트 등록
	 * @param test TestVO
	 * @return ServiceResult 성공 ServiceResult.OK : 실패 ServiceResult.FAIL
	 */
	public ServiceResult createTest(TestVOWrapper testVOWrapper);
	
	/**
	 * 유형별 테스트 리스트 조회
	 * @param testCd 시험코드(인성, 적성, 입사, 코딩테스트)
	 * @return List<TestVO> 
	 */
	public List<TestVO> readTestList(PaginationInfo paging, String testCd, @Param("userId") String userId);
	
	/**
	 * 유형별 테스트 리스트의 건수
	 * @param paging PaginationInfo
	 * @param testCd 시험코드(인성, 적성, 입사, 코딩테스트)
	 * @return int 
	 */
	public int readTotalTest(PaginationInfo paging, String testCd, @Param("userId") String userId);
	
	/**
	 * 테스트 상세조회
	 * @param testNo 시험번호
	 * @return TestVO
	 */
	public TestVO readTest(int testNo);
	
	/**
	 * 테스트 수정
	 * @param test TestVO
	 * @return ServiceResult 성공 ServiceResult.OK : 실패 ServiceResult.FAIL
	 */
	public ServiceResult modifyTest(TestVOWrapper testVOWrapper);
	
	/**
	 * 테스트 삭제여부 수정
	 * @param testNo 시험번호
	 * @return ServiceResult 성공 ServiceResult.OK : 실패 ServiceResult.FAIL
	 */
	public ServiceResult removeTest(int testNo);
	
	/**
	 * 파일 한건 삭제
	 * @param atchFileNo
	 * @param fileSn
	 */
	public void removeFile(int atchFileNo, int fileSn);
	
	/**
	 * 테스트 리스트에서 다건 삭제
	 * @param testNo 시험번호
	 * @return ServiceResult 성공 ServiceResult.OK : 실패 ServiceResult.FAIL
	 */
	public ServiceResult removeCompTestList(List<Integer> testNo);
}
