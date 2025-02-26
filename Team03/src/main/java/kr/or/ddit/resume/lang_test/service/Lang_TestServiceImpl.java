package kr.or.ddit.resume.lang_test.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.resume.lang_test.dao.Lang_TestMapper;
import kr.or.ddit.vo.Lang_TestVO;

@Service
public class Lang_TestServiceImpl implements Lang_TestService {
	
	@Inject
	Lang_TestMapper mapper;
	
	@Override
	public ServiceResult createLangTest(Lang_TestVO langTestVO) {
		if(mapper.insertLangTest(langTestVO) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public List<Lang_TestVO> readLangtestList(String memId) {
		return mapper.selectLangtestList(memId);
	}

	@Override
	public ServiceResult modifiyLangTest(Lang_TestVO langTestVO) {
		if(mapper.updateLangTest(langTestVO) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public ServiceResult removeLangTest(int langTestNo) {
		if(mapper.deleteLangTest(langTestNo) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public Lang_TestVO readLangtest(int langTestNo) {
		return mapper.selectLangtest(langTestNo);
	}

}
