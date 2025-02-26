package kr.or.ddit.resume.language.service;

import java.util.List;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.vo.LanguageVO;

public interface LanguageService {
	public ServiceResult createLanguage(LanguageVO languageVO);
	
	public List<LanguageVO> readLanguageList(String memId);
	
	public ServiceResult modifiyLanguage(LanguageVO languageVO);
	
	public ServiceResult removeLanguage(int langNo);

	public LanguageVO readLanguage(int langNo);
}
