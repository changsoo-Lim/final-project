package kr.or.ddit.resume.qualfication.service;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.vo.QualificationVO;

public interface QualficationService {
	/**
	 * 검정 고시 등록
	 * 
	 * @param qualificationVO
	 * @return
	 */
	public ServiceResult createQualfication(QualificationVO qualificationVO);

	/**
	 * 검정고시 조회
	 * 
	 * @param memId
	 * @return
	 */
	public QualificationVO readQualificationList(String memId);

	/**
	 * 검정고시 수정
	 * 
	 * @param qualificationVO
	 * @return
	 */
	public ServiceResult modifiyQualfication(QualificationVO qualificationVO);

}
