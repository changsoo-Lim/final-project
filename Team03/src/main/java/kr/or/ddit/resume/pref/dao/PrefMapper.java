package kr.or.ddit.resume.pref.dao;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.PrefVO;

@Mapper
public interface PrefMapper {

	/**
	 * @param prefVO
	 * @return
	 */
	public int insertPref(PrefVO prefVO);
	
	public PrefVO selectPref(String memId);
	
	public int updatePref(PrefVO prefVO);

	public int deletePref(int prefNo);
	
	public PrefVO selectPrefDetail(int prefNo);
}
