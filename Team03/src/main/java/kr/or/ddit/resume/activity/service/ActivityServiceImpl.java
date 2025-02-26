package kr.or.ddit.resume.activity.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.resume.activity.dao.ActivityMapper;
import kr.or.ddit.vo.ActivityVO;

@Service
public class ActivityServiceImpl implements ActivityService {
	
	@Inject
	ActivityMapper mapper;
	
	@Override
	public ServiceResult createActivity(ActivityVO activityVO) {
		if(mapper.insertActivity(activityVO) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public List<ActivityVO> readActivityList(String memId) {
		return mapper.selectActivityList(memId);
	}

	@Override
	public ServiceResult modifiyActivity(ActivityVO activityVO) {
		if(mapper.updateActivity(activityVO) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public ServiceResult removeActivity(int activityNo) {
		if(mapper.deleteActivity(activityNo) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public ActivityVO readActivity(int activityNo) {
		return mapper.selectActivity(activityNo);
	}

}
