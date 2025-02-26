package kr.or.ddit.resume.experience.service;

import java.util.List;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.vo.ExperienceVO;

public interface ExperienceService {
	public ServiceResult createExperience(ExperienceVO experienceVO);
	
	public List<ExperienceVO> readExperienceList(String memId);
	
	public ServiceResult modifiyExperience(ExperienceVO experienceVO);
	
	public ServiceResult removeExperience(int expNo);
	
	public ExperienceVO readExperience(int expNo);
}
