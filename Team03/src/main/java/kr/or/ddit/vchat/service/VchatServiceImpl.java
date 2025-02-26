package kr.or.ddit.vchat.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.vchat.dao.VchatMapper;
import kr.or.ddit.vo.EmployVO;
import kr.or.ddit.vo.FieldVO;
import kr.or.ddit.vo.VchatVO;

@Service
public class VchatServiceImpl implements VchatService {
	
	@Inject
	VchatMapper mapper;

	@Override
	public ServiceResult createVacht(VchatVO vchat) {
		if(mapper.insertVacht(vchat) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public List<EmployVO> employAndFieldList(String compId) {
		return mapper.employAndFieldList(compId);
	}

	@Override
	public List<VchatVO> readVchatList(String compId) {
		return mapper.selectVchatList(compId);
	}

	@Override
	public ServiceResult removeVchat(int vchatNo) {
		if(mapper.deleteVchat(vchatNo) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public List<EmployVO> employAndFieldMemList(String memId) {
		return mapper.employAndFieldMemList(memId);
	}

	@Override
	public List<VchatVO> readVchatMemList(String memId) {
		return mapper.selectVchatMemList(memId);
	}

}
