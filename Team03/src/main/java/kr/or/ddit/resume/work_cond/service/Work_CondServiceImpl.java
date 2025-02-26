package kr.or.ddit.resume.work_cond.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.resume.work_cond.dao.Work_CondMapper;
import kr.or.ddit.vo.Work_CityVO;
import kr.or.ddit.vo.Work_CondVO;

@Service
public class Work_CondServiceImpl implements Work_CondService {
	
	@Inject
	Work_CondMapper mapper;
	
	@Override
	public ServiceResult createWorkCond(Work_CondVO workCondVO) {
		if(mapper.insertWorkCond(workCondVO) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public ServiceResult createWorkCity(Work_CityVO workCityVO) {
		if(mapper.insertWorkCity(workCityVO) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public Work_CondVO selectWorkCondOne(String memId) {
	    return Optional.ofNullable(mapper.selectWorkCondOne(memId))
	                   .orElse(new Work_CondVO());
	}

	@Override
	public List<Work_CityVO> selectWorkCityList(int workCondNo) {
	    return Optional.ofNullable(mapper.selectWorkCityList(workCondNo))
	                   .orElseGet(ArrayList::new);
	}


	@Override
	public ServiceResult modifiyWorkCond(Work_CondVO workCondVO) {
		if(mapper.updateWorkCond(workCondVO) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	public ServiceResult modifiyWorkCity(Work_CityVO workCityVO) {
		if(mapper.updateWorkCity(workCityVO) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public ServiceResult removeWorkCond(int workCondNo) {
		if(mapper.deleteWorkCond(workCondNo) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}
	
	@Override
	public ServiceResult deleteWorkCity(int workCityNo) {
		if(mapper.deleteWorkCity(workCityNo) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public List<Work_CityVO> readWorkCity(int workCityNo) {
		return Optional.ofNullable(mapper.selectWorkCity(workCityNo))
				.orElseGet(ArrayList::new);
	}
	
	@Override
	public Work_CondVO readWorkCond(int workCondNo) {
	    return Optional.ofNullable(mapper.selectWorkCond(workCondNo))
	                   .orElse(new Work_CondVO());
	}


}
