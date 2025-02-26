package kr.or.ddit.resume.work_cond.service;

import java.util.List;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.vo.Work_CityVO;
import kr.or.ddit.vo.Work_CondVO;

public interface Work_CondService {
	public ServiceResult createWorkCond(Work_CondVO workCondVO);
	
	public ServiceResult createWorkCity(Work_CityVO workCityVO);
	
	public Work_CondVO selectWorkCondOne(String memId);
	
	public List<Work_CityVO> selectWorkCityList(int workCondNo);
	
	public ServiceResult modifiyWorkCond(Work_CondVO workCondVO);
	
	public ServiceResult modifiyWorkCity(Work_CityVO workCityVO);
	
	public ServiceResult removeWorkCond(int workCondNo);
	
	public ServiceResult deleteWorkCity(int workCityNo);
	
	public List<Work_CityVO> readWorkCity(int workCityNo);
	
	public Work_CondVO readWorkCond(int workCondNo);

}
