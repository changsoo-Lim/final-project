package kr.or.ddit.resume.resume.service;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.resume.resume.ResumeDTO;
import kr.or.ddit.vo.MemberVO;

public interface ResumeService {
	
	/**
	 * 이력서 등록
	 * @param resume
	 * @return ok fail
	 */
	public ServiceResult insertAndUpdateResume(String memId,ResumeDTO resume);
	
	public ServiceResult updateMemberImage(MemberVO member);
}
