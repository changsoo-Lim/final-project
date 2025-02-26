package kr.or.ddit.status.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.status.dao.StatusMapper;
import kr.or.ddit.vo.StatusVO;

@Service
public class StatusServiceImpl implements StatusService {
	
	@Inject
	StatusMapper mapper;

	@Override
	public StatusVO readStatus(String memId) {
		return mapper.selectStatus(memId);
	}

	@Override
	public ServiceResult modifiyStatus(String memId,String type,String state) {
		if(mapper.updateStatus(memId, type, state) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}
}
