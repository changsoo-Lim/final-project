package kr.or.ddit.resume.experience.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.ExperienceVO;

@Mapper
public interface ExperienceMapper {
	
	/**
	 * @param experienceVO
	 * @return
	 */
	public int insertExperience(ExperienceVO experienceVO);
	
	public List<ExperienceVO> selectExperienceList(String memId);
	
	public int updateExperience(ExperienceVO experienceVO);

	public int deleteExperience(int expNo);
	
	public ExperienceVO selectExperience(int expNo);
}
