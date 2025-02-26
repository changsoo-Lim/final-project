package kr.or.ddit.company.compcontent.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.CompanyVO;

@Mapper
public interface CompanyContentMapper {
	
	/**
	 * 전체기업 소개 조회 
	 * @return 기업VO 리스트
	 */
	public List<CompanyVO> selectCompContentList();
	
	/**
	 * 기업 소개 단일 조회
	 * @param compId (조회할 기업 아이디)
	 * @return 기업VO
	 */
	public CompanyVO selectCompContent(String compId);
	
	/**
	 * 기업 소개 작성
	 * @param compVO ( 생성할 기업 소개 내용)
	 * @return 성공 여부
	 */
	public int insertCompContent(CompanyVO compVO);
	
	/**
	 * 기업 소개 업데이트
	 * @param compVO (업데이트할 기업소개 내용)
	 * @return 성공 여부
	 */
	public int updateCompContent(CompanyVO compVO);
	
	/**
	 * 기업 소개 삭제 
	 * @param compId (삭제할 기업ID , 삭제여부 N 으로 업데이트)
	 * @return 성공 여부
	 */
	public int deleteCompContent(String compId);
	
}
