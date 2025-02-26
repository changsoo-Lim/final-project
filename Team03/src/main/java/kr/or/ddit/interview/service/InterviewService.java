package kr.or.ddit.interview.service;

import kr.or.ddit.commons.enumpkg.ServiceResult;

public interface InterviewService {
	
	/**
	 * 면접 진행 후 상태 Y
	 * @param intvNo
	 * @return
	 */
	public ServiceResult modifiyInterview(int intvNo);
	
}
