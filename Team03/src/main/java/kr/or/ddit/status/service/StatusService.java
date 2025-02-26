package kr.or.ddit.status.service;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.vo.StatusVO;

public interface StatusService {
	
	/**
	 * 회원의 알림 수신 여부
	 * @param memId
	 * @return
	 */
	public StatusVO readStatus(String memId);
	
	/**
	 * 조건에 따른 알림 수신여부 변경
	 * @param memId
	 * @return
	 */
	public ServiceResult modifiyStatus(String memId,String type,String state);

}
