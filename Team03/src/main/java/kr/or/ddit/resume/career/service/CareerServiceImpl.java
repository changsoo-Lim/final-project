package kr.or.ddit.resume.career.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.resume.career.dao.CareerMapper;
import kr.or.ddit.vo.CareerVO;

@Service
public class CareerServiceImpl implements CareerService {

	@Inject
	CareerMapper mapper;
	
	@Override
	public ServiceResult createCareer(CareerVO careerVO) {
		if(mapper.insertCareer(careerVO) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public List<CareerVO> readCareerList(String memId) {
		return mapper.selectCareerList(memId);
	}

	@Override
	public ServiceResult modifiyCareer(CareerVO careerVO) {
		if(mapper.updateCareer(careerVO) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public ServiceResult removeCareer(int careerNo) {
		if(mapper.deleteCareer(careerNo) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}
	
	@Override
	public CareerVO readCareer(int careerNo) {
		return mapper.selectCareer(careerNo);
	}


}
