package kr.or.ddit.interview.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.interview.dao.InterviewMapper;

@Service
public class InterviewServiceImpl implements InterviewService {
	
	@Inject
	InterviewMapper mapper;

	@Override
	public ServiceResult modifiyInterview(int intvNo) {
		if(mapper.updateInterview(intvNo) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

}
