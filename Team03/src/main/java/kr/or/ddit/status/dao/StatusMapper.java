package kr.or.ddit.status.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.vo.StatusVO;

@Mapper
public interface StatusMapper {
	
	/**
	 * 회원의 알림 수신 여부
	 * @param memId
	 * @return
	 */
	public StatusVO selectStatus(String memId);
	
	/**
	 * 조건에 따른 알림 수신여부 변경
	 * @param memId
	 * @return
	 */
	public int updateStatus(@Param("memId") String memId, @Param("type") String type, @Param("state") String state);
	
}
