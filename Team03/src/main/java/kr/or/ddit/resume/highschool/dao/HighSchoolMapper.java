package kr.or.ddit.resume.highschool.dao;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.HighSchoolVO;

@Mapper
public interface HighSchoolMapper {
	
	/**
	 * 고등학교 등록
	 * @param highSchoolVO
	 * @return
	 */
	public int insertHighschool(HighSchoolVO highSchoolVO);
	
	/**
	 * 고등학교 조회
	 * @param memId
	 * @return
	 */
	public HighSchoolVO selectHighshcool(String memId);
	
	/**
	 * 고등학교 업데이트
	 * @param highSchoolVO
	 * @return
	 */
	public int updateHighSchool(HighSchoolVO highSchoolVO);
}
