package kr.or.ddit.resume.cret.service;

import java.util.List;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.vo.CertVO;

public interface CertService {
	public ServiceResult createCert(CertVO certVO);
	
	public List<CertVO> readCertList(String memId);
	
	public ServiceResult modifiyCert(CertVO certVO);
	
	public ServiceResult removeCert(int certNo);
	
	public CertVO readCert(int certNo);
}
