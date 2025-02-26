package kr.or.ddit.resume.award.service;

import java.util.List;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.vo.AwardVO;

public interface AwardService {
	public ServiceResult createAward(AwardVO awardVO);
	
	public List<AwardVO> readAwardList(String memId);
	
	public ServiceResult modifiyAward(AwardVO awardVO);
	
	public ServiceResult removeAward(int awardNo);

	public AwardVO readAward(int awardNo);
}
