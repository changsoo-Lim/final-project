package kr.or.ddit.resume.activity.service;

import java.util.List;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.vo.ActivityVO;

public interface ActivityService {
	public ServiceResult createActivity(ActivityVO activityVO);
	
	public List<ActivityVO> readActivityList(String memId);
	
	public ServiceResult modifiyActivity(ActivityVO activityVO);
	
	public ServiceResult removeActivity(int activityNo);
	
	public ActivityVO readActivity(int activityNo);
}
