package kr.or.ddit.resume.pref.service;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.vo.PrefVO;

public interface PrefService {
	
	public ServiceResult createPref(PrefVO prefVO);
	
	public PrefVO readPref(String memId);
	
	public ServiceResult modifiyPref(PrefVO prefVO);
	
	public ServiceResult removePref(int prefNo);
	
	public PrefVO readPrefDetail(int prefNo);
}
