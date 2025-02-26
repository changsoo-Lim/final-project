package kr.or.ddit.resume.highschool.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.resume.highschool.dao.HighSchoolMapper;
import kr.or.ddit.vo.HighSchoolVO;

@Service
public class HighSchoolServiceImpl implements HighSchoolService {

	@Inject
	HighSchoolMapper mapper;

	@Override
	public ServiceResult createHighschool(HighSchoolVO highSchoolVO) {
		if (mapper.insertHighschool(highSchoolVO) > 0) {
			return ServiceResult.OK;
		} else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public HighSchoolVO readHighshcool(String memId) {
		return mapper.selectHighshcool(memId);
	}

	@Override
	public ServiceResult modifiyHighSchool(HighSchoolVO highSchoolVO) {
		if (mapper.updateHighSchool(highSchoolVO) > 0) {
			return ServiceResult.OK;
		} else {
			return ServiceResult.FAIL;
		}
	}

}
