package kr.or.ddit.resume.edu.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.resume.edu.dao.EduMapper;
import kr.or.ddit.vo.EduVO;

@Service
public class EduServiceImpl implements EduService {
	
	@Inject
	EduMapper mapper;

	@Override
	public ServiceResult createEdu(EduVO eduVO) {
		if(mapper.insertEdu(eduVO) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public List<EduVO> readEduList(String memId) {
		return mapper.selectEduList(memId);
	}

	@Override
	public ServiceResult modifiyEdu(EduVO eduVO) {
		if(mapper.updateEdu(eduVO) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public ServiceResult removeEdu(int eduNo) {
		if(mapper.deleteEdu(eduNo) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public EduVO readEdu(int eduNo) {
		return mapper.selectEdu(eduNo);
	}

}
