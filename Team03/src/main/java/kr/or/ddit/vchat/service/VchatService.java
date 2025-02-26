package kr.or.ddit.vchat.service;

import java.util.List;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.vo.EmployVO;
import kr.or.ddit.vo.FieldVO;
import kr.or.ddit.vo.VchatVO;

public interface VchatService {
	
	/**
	 * 구르미 api 써서 필요한 것들만 insert
	 * @param vchat
	 * @return
	 */
	public ServiceResult createVacht(VchatVO vchat);
	
	/**
	 * 기업에 따른 채용공고와 모집분야
	 * @param compId
	 * @return
	 */
	public List<EmployVO> employAndFieldList(String compId);

	/**
	 * 화상채팅 방 리스트
	 * @param compId
	 * @return
	 */
	public List<VchatVO> readVchatList(String compId);
	
	/**
	 * 채팅 방 삭제
	 * @param vchatNo
	 * @return
	 */
	public ServiceResult removeVchat(int vchatNo);
	
	/**
	 * 회원이 지원한 따른 채용공고와 모집분야
	 * @param memId
	 * @return
	 */
	public List<EmployVO> employAndFieldMemList(String memId);
	
	/**
	 * 회원 화상채팅 방 리스트
	 * @param memId
	 * @return
	 */
	public List<VchatVO> readVchatMemList(String memId);
}
