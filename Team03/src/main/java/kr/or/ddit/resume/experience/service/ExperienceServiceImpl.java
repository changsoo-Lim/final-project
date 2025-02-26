package kr.or.ddit.resume.experience.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.resume.experience.dao.ExperienceMapper;
import kr.or.ddit.vo.ExperienceVO;

@Service
public class ExperienceServiceImpl implements ExperienceService {
	
	@Inject
	ExperienceMapper mapper;
	
	@Override
	public ServiceResult createExperience(ExperienceVO experienceVO) {
		if(mapper.insertExperience(experienceVO) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
		
	}

	@Override
	public List<ExperienceVO> readExperienceList(String memId) {
		return mapper.selectExperienceList(memId);
	}

	@Override
	public ServiceResult modifiyExperience(ExperienceVO experienceVO) {
		if(mapper.updateExperience(experienceVO) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public ServiceResult removeExperience(int expNo) {
		if(mapper.deleteExperience(expNo) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public ExperienceVO readExperience(int expNo) {
		return mapper.selectExperience(expNo);
	}

}
