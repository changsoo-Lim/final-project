package kr.or.ddit.resume.edu.service;

import java.util.List;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.vo.EduVO;

public interface EduService {
	public ServiceResult createEdu(EduVO eduVO);
	
	public List<EduVO> readEduList(String memId);
	
	public ServiceResult modifiyEdu(EduVO eduVO);
	
	public ServiceResult removeEdu(int eduNo);

	public EduVO readEdu(int eduNo);
}
