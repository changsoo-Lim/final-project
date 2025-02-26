package kr.or.ddit.intro.intro.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.intro.intro.dao.IntroMapper;
import kr.or.ddit.vo.IntroVO;

@Service
public class IntroServiceImpl implements IntroService {
	
	@Inject
	private IntroMapper dao;

	@Override
	public List<IntroVO> readListIntro(String memId) {
		// TODO Auto-generated method stub
		
		return dao.selectListIntro(memId);
	}

	@Override
	public IntroVO readIntro(int introNo) {
		return dao.selectIntro(introNo);
	}
	
	@Override
	public ServiceResult createIntro(IntroVO introVO) {
		
		if(dao.insertIntro(introVO) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}
	
	@Override
	public ServiceResult modifyIntro(IntroVO introVO) {
		if(dao.updateIntro(introVO) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public ServiceResult removeIntro(String introNo) {
		if(dao.deleteIntro(introNo) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

}
