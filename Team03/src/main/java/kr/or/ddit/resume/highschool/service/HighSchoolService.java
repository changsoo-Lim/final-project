package kr.or.ddit.resume.highschool.service;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.vo.HighSchoolVO;

public interface HighSchoolService {
	
	/**
	 * 고등학교 등록
	 * @param highSchoolVO
	 * @return
	 */
	public ServiceResult createHighschool(HighSchoolVO highSchoolVO);
	
	/**
	 * 고등학교 조회
	 * @param memId
	 * @return
	 */
	public HighSchoolVO readHighshcool(String memId);
	
	/**
	 * 고등학교 업데이트
	 * @param highSchoolVO
	 * @return
	 */
	public ServiceResult modifiyHighSchool(HighSchoolVO highSchoolVO);
}
