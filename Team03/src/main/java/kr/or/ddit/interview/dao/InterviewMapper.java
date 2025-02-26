package kr.or.ddit.interview.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InterviewMapper {
	
	/**
	 * 면접 진행 후 상태 Y
	 * @param intvNo
	 * @return
	 */
	public int updateInterview(int intvNo);

}
