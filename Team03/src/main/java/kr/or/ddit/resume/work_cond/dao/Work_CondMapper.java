package kr.or.ddit.resume.work_cond.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.Work_CityVO;
import kr.or.ddit.vo.Work_CondVO;

@Mapper
public interface Work_CondMapper {

	/**
	 * @param workCondVO
	 * @return
	 */
	public int insertWorkCond(Work_CondVO workCondVO);
	
	public int insertWorkCity(Work_CityVO workCityVO);
	
	public Work_CondVO selectWorkCondOne(String memId);
	
	public List<Work_CityVO> selectWorkCityList(int workCondNo);
	
	public int updateWorkCond(Work_CondVO workCondVO);
	
	public int updateWorkCity(Work_CityVO workCityVO);
	
	public int deleteWorkCond(int workCondNo);
	
	public int deleteWorkCity(int workCityNo);
	
	public List<Work_CityVO> selectWorkCity(int workCityNo);
	
	public Work_CondVO selectWorkCond(int workCondNo);
}
