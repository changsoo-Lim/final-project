package kr.or.ddit.resume.cret.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.CertVO;

@Mapper
public interface CretMapper {
	
	/**
	 * @param certVO
	 * @return
	 */
	public int insertCert(CertVO certVO);
	
	public List<CertVO> selectCertList(String memId);
	
	public int updateCert(CertVO certVO);
	
	public int deleteCert(int certNo);
	
	public CertVO selectCert(int certNo);
}
