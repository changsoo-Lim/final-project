package kr.or.ddit.resume.compu.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.resume.compu.dao.CompuMapper;
import kr.or.ddit.vo.CompVO;

@Service
public class CompServiceImpl implements CompService {
	
	@Inject
	CompuMapper mapper;
	
	@Override
	public ServiceResult createComp(CompVO compVO) {
		if(mapper.insertComp(compVO) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public List<CompVO> readCompList(String memId) {
		return mapper.selectCompList(memId);
	}

	@Override
	public ServiceResult modifiyComp(CompVO compVO) {
		if(mapper.updateComp(compVO) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public ServiceResult removeComp(int compNo) {
		if(mapper.deleteComp(compNo) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public CompVO readComp(int compNo) {
		return mapper.selectComp(compNo);
	}

}
