package kr.or.ddit.resume.pref.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.resume.pref.dao.PrefMapper;
import kr.or.ddit.vo.PrefVO;

@Service
public class PrefServiceImpl implements PrefService {
	
	@Inject
	PrefMapper mapper;
	
	@Override
	public ServiceResult createPref(PrefVO prefVO) {
		if(mapper.insertPref(prefVO) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public PrefVO readPref(String memId) {
		return mapper.selectPref(memId);
	}

	@Override
	public ServiceResult modifiyPref(PrefVO prefVO) {
		if(mapper.updatePref(prefVO) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public ServiceResult removePref(int prefNo) {
		if(mapper.deletePref(prefNo) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public PrefVO readPrefDetail(int prefNo) {
		return mapper.selectPrefDetail(prefNo);
	}

}
