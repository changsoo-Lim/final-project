package kr.or.ddit.resume.uni.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.resume.uni.dao.UniMapper;
import kr.or.ddit.vo.UniVO;

@Service
public class UniServiceImpl implements UniService {
	
	@Inject
	UniMapper mapper;
	
	@Override
	public ServiceResult createUni(UniVO uniVO) {
		if(mapper.insertUni(uniVO) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public List<UniVO> readUniList(String memId) {
		
		return mapper.selectUniList(memId);
	}

	@Override
	public ServiceResult modifiyUni(UniVO uniVO) {
		if(mapper.updateUni(uniVO) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public ServiceResult deleteUni(int uniNo) {
		if(mapper.deleteUni(uniNo) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public UniVO selectUni(int uniNo) {
		return mapper.selectUni(uniNo);
	}

}
