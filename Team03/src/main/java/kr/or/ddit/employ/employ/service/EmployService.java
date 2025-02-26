package kr.or.ddit.employ.employ.service;

import java.text.ParseException;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.EmployVO;

public interface EmployService {
	/**
	 * 단건조회 : 채용공고 상세조회
	 * @param employNo
	 * @return EmployVO
	 * @throws ParseException 
	 */
	public EmployVO readEmploy(int employNo, String memId) throws ParseException;
	
	/**
	 * 다건조회 : 채용공고 리스트 조회
	 * @param paging 페이징 및 검색조건
	 * @return 기업ID null일 경우 채용공고 전체, null이 아닐 경우 해당기업 채용공고 리스트
	 */
	public List<EmployVO> readEmployList(@Param("paging") PaginationInfo<EmployVO> paging
											, @Param("companyId") String companyId, @Param("memId") String memId);
	
	/**
	 * 등록 : 채용공고 등록
	 * @param employ
	 * @return
	 */
	public int createEmploy(EmployVO employ);
	
	/**
	 * 수정 : 채용공고 수정
	 * @param employ
	 * @return 
	 */
	public int modifyEmploy(EmployVO employ);

	/**
	 * 삭제 : 해당 공고 삭제(삭제여부 Y로 수정)
	 * @param employNo
	 * @return 성공시 : 1, 실패시 : 0
	 */
	public int removeEmploy(int employNo);
	
	/**
	 * 필수조건 타이틀 리스트 출력
	 * @return CodeVO
	 */
	public List<CodeVO> readFilterTitleList();
	/**
	 * 필수조건 내용 리스트 출력
	 * @return CodeVO
	 */
	public List<CodeVO> readFilterContList();
}
