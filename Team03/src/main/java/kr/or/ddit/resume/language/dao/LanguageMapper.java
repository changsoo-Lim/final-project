package kr.or.ddit.resume.language.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.LanguageVO;

@Mapper
public interface LanguageMapper {
	
	/**
	 * @param languageVO
	 * @return
	 */
	public int insertLanguage(LanguageVO languageVO);
	
	public List<LanguageVO> selectLanguageList(String memId);
	
	public int updateLanguage(LanguageVO languageVO);

	public int deleteLanguage(int langNo);

	public LanguageVO selectLanguage(int langNo);
}
