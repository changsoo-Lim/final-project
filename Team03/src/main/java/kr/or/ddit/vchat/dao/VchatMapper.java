package kr.or.ddit.vchat.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.EmployVO;
import kr.or.ddit.vo.FieldVO;
import kr.or.ddit.vo.VchatVO;

@Mapper
public interface VchatMapper {
	
	/**
	 * 구르미 api 써서 필요한 것들만 insert
	 * @param vchat
	 * @return
	 */
	public int insertVacht(VchatVO vchat);
	
	/**
	 * 기업에 따른 채용공고와 모집분야
	 * @param compId
	 * @return
	 */
	public List<EmployVO> employAndFieldList(String compId);
	
	/**
	 * 기업 화상채팅 방 리스트
	 * @param compId
	 * @return
	 */
	public List<VchatVO> selectVchatList(String compId);
	
	/**
	 * 기업 화상채팅 삭제
	 * @param vchatNo
	 * @return
	 */
	public int deleteVchat(int vchatNo);

	// -- 회원 --
	
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
	public List<VchatVO> selectVchatMemList(String memId);
	
}
