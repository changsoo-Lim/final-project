package kr.or.ddit.resume.award.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.resume.award.dao.AwardMapper;
import kr.or.ddit.vo.AwardVO;

@Service
public class AwardServiceImpl implements AwardService {

	@Inject
	AwardMapper mapper;
	
	@Override
	public ServiceResult createAward(AwardVO awardVO) {
		if(mapper.insertAward(awardVO) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public List<AwardVO> readAwardList(String memId) {
		return mapper.selectAwardList(memId);
	}

	@Override
	public ServiceResult modifiyAward(AwardVO awardVO) {
		if(mapper.updateAward(awardVO) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public ServiceResult removeAward(int awardNo) {
		if(mapper.deleteAward(awardNo) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public AwardVO readAward(int awardNo) {
		return mapper.selectAward(awardNo);
	}

}
