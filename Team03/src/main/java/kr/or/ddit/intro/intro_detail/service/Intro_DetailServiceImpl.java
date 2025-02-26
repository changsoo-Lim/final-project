package kr.or.ddit.intro.intro_detail.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.intro.intro_detail.dao.Intro_DetailMapper;
import kr.or.ddit.vo.Intro_DetailVO;

@Service
public class Intro_DetailServiceImpl implements Intro_DetailService {
	
	@Inject
	private Intro_DetailMapper dao;

	@Override
	public Intro_DetailVO readIntroDetail(Intro_DetailVO introDetailVO) {
		return dao.selectIntroDetail(introDetailVO);
	}

	@Override
	public List<Intro_DetailVO> readListIntroDetail(int introNo) {
		return dao.selectListIntroDetail(introNo);
	}

	@Override
	public ServiceResult createIntroDetail(Intro_DetailVO introDetailVO) {
		if(dao.insertIntroDetail(introDetailVO)>0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}


	@Override
	public ServiceResult modifyIntroDetail(Intro_DetailVO introDetailVO) {
		if(dao.updateIntroDetail(introDetailVO)>0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public ServiceResult removeIntroDetail(int introDetailNo) {
		if(dao.deleteIntroDetail(introDetailNo)>0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}


}
