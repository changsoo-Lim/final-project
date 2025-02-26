package kr.or.ddit.resume.cret.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.resume.cret.dao.CretMapper;
import kr.or.ddit.vo.CertVO;

@Service
public class CertServiceImpl implements CertService {
	
	@Inject
	CretMapper mapper;
	
	@Override
	public ServiceResult createCert(CertVO certVO) {
		if(mapper.insertCert(certVO) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public List<CertVO> readCertList(String memId) {
		return mapper.selectCertList(memId);
	}

	@Override
	public ServiceResult modifiyCert(CertVO certVO) {
		if(mapper.updateCert(certVO) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public ServiceResult removeCert(int certNo) {
		if(mapper.deleteCert(certNo) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public CertVO readCert(int certNo) {
		return mapper.selectCert(certNo);
	}

}
