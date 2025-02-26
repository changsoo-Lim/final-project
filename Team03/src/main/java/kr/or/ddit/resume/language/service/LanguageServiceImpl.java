package kr.or.ddit.resume.language.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.resume.language.dao.LanguageMapper;
import kr.or.ddit.vo.LanguageVO;

@Service
public class LanguageServiceImpl implements LanguageService {
	
	
	@Inject
	LanguageMapper mapper;
	
	@Override
	public ServiceResult createLanguage(LanguageVO languageVO) {
		if(mapper.insertLanguage(languageVO) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public List<LanguageVO> readLanguageList(String memId) {
		return mapper.selectLanguageList(memId);
	}

	@Override
	public ServiceResult modifiyLanguage(LanguageVO languageVO) {
		if(mapper.updateLanguage(languageVO) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public ServiceResult removeLanguage(int langNo) {
		if(mapper.deleteLanguage(langNo) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public LanguageVO readLanguage(int langNo) {
		return mapper.selectLanguage(langNo);
	}

}
