package kr.or.ddit.employ.employ.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.EmployVO;
import kr.or.ddit.vo.FieldVO;
import kr.or.ddit.vo.FilterVO;
import kr.or.ddit.vo.ProcedureVO;


/**
 * 채용공고 MAPPER
 * 단건조회 : ﻿/empoly/{employNo} GET
 * 다건조회 : /empoly GET
 * 공고등록 : /empoly POST
 * 공고수정 : /empoly/{employNo} PUT
 * 공고삭제 : /empoly/{employNo} PUT (삭제여부 Y로 수정)
 */
@Mapper
public interface EmployMapper {
	
	/**
	 * 메인페이지 단건조회 : 채용공고 상세조회
	 * @param employNo
	 * @return EmployVO
	 */
	public EmployVO selectEmploy(@Param("employNo") int employNo, @Param("memId") String memId);
	/**
	 * 목록 수 조회
	 * @param paging
	 * @return
	 */
	public int selectTotalRecord(@Param("paging") PaginationInfo<EmployVO> paging
									, @Param("companyId") String companyId, @Param("memId") String memId);
	/**
	 * 다건조회 : 채용공고 리스트 조회
	 * @param paging 페이징 및 검색조건
	 * @return 기업ID null일 경우 채용공고 전체, null이 아닐 경우 해당기업 채용공고 리스트
	 */
	public List<EmployVO> selectEmployList(@Param("paging") PaginationInfo<EmployVO> paging
											, @Param("companyId") String companyId, @Param("memId") String memId);
	
	/**
	 * 모집분야 리스트
	 * @return
	 */
	public List<FieldVO> selectFieldList();
	/**
	 * 필수조건 리스트
	 * @return
	 */
	public List<FilterVO> selectFilterList();
	/**
	 * 채용절차 리스트
	 * @return
	 */
	public List<ProcedureVO> selectProcedureList();
	/**
	 * 등록 : 채용공고 등록
	 * @param employ
	 * @return
	 */
	public int insertEmploy(EmployVO employ);
	/**
	 * 등록 : 모집 분야 등록
	 * @param field
	 * @return
	 */
	public int insertField(FieldVO field);
	/**
	 * 등록 : 채용절차 등록
	 * @param procedure
	 * @return
	 */
	public int insertProcedure(ProcedureVO procedure);
	/**
	 * 등록 : 필수 조건 등록
	 * @param procedure
	 * @return
	 */
	public int insertFilter(FilterVO filter);
	/**
	 * 수정 : 채용공고 수정
	 * @param employ
	 * @return 
	 */
	public int updateEmploy(EmployVO employ);

	/**
	 * 삭제 : 해당 공고 삭제(삭제여부 Y로 수정)
	 * @param employNo
	 * @return 성공시 : 1, 실패시 : 0
	 */
	public int deleteEmploy(int employNo);
	/**
	 * 삭제 : 모집분야 삭제
	 * @param employNo
	 * @return 성공시 : 1, 실패시 : 0
	 */
	public int deleteField(int employNo);
	/**
	 * 삭제 : 필수조건 삭제
	 * @param employNo
	 * @return 성공시 : 1, 실패시 : 0
	 */
	public int deleteFilter(int filedNo);
	/**
	 * 삭제 : 채용절차 삭제
	 * @param employNo
	 * @return 성공시 : 1, 실패시 : 0
	 */
	public int deleteProcedure(int filedNo);
	
	/**
	 * 채용공고 조회수 카운트
	 * 
	 * @param boNo
	 * @return
	 */
	public int incrementHit(int boNo);
	
	/**
	 * 필수조건 타이틀 리스트 출력
	 * @return CodeVO
	 */
	public List<CodeVO> selectFilterTitleList();
	/**
	 * 필수조건 내용 리스트 출력
	 * @return CodeVO
	 */
	public List<CodeVO> selectFilterContList();
	
	/**
	 * 공고 지원자수 조회
	 * @param employVO
	 */
	public int selelctEmpApplyCnt(Integer integer);
}
