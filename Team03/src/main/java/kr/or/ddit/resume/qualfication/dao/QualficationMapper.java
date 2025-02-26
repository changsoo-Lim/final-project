package kr.or.ddit.resume.qualfication.dao;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.QualificationVO;

@Mapper
public interface QualficationMapper {

	/**
	 * 검정 고시 등록
	 * @param qualificationVO
	 * @return
	 */
	public int insertQualfication(QualificationVO qualificationVO);
	
	/**
	 * 검정고시 조회
	 * @param memId
	 * @return
	 */
	public QualificationVO selectQualificationList(String memId);
	
	/**
	 * 검정고시 수정
	 * @param qualificationVO
	 * @return
	 */
	public int updateQualfication(QualificationVO qualificationVO);
}
