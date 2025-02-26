package kr.or.ddit.resume.compu.service;

import java.util.List;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.vo.CompVO;

public interface CompService {
	public ServiceResult createComp(CompVO compVO);
	
	public List<CompVO> readCompList(String memId);
	
	public ServiceResult modifiyComp(CompVO compVO);
	
	public ServiceResult removeComp(int compNo);
	
	public CompVO readComp(int compNo);
}
