package kr.or.ddit.resume.uni.service;

import java.util.List;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.vo.UniVO;

public interface UniService {
	
	public ServiceResult createUni(UniVO uniVO);
	
	public List<UniVO> readUniList(String memId);
	
	public ServiceResult modifiyUni(UniVO uniVO);
	
	public ServiceResult deleteUni(int uniNo);
	
	public UniVO selectUni(int uniNo);
}
