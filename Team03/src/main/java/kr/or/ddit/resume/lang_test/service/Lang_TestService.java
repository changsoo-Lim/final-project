package kr.or.ddit.resume.lang_test.service;

import java.util.List;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.vo.Lang_TestVO;

public interface Lang_TestService {
	public ServiceResult createLangTest(Lang_TestVO langTestVO);
	
	public List<Lang_TestVO> readLangtestList(String memId);
	
	public ServiceResult modifiyLangTest(Lang_TestVO langTestVO);
	
	public ServiceResult removeLangTest(int langTestNo);

	public Lang_TestVO readLangtest(int langTestNo);
}
