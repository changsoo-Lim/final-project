package kr.or.ddit.resume.activity.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.ActivityVO;


@Mapper
public interface ActivityMapper {
	/**
	 * 봉사활동 등록
	 * @param activityVO
	 * @return
	 */
	public int insertActivity(ActivityVO activityVO);
	
	public List<ActivityVO> selectActivityList(String memId);
	
	public int updateActivity(ActivityVO activityVO);

	public int deleteActivity(int activityNo);
	
	public ActivityVO selectActivity(int activityNo);
}
