package kr.or.ddit.resume.uni.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.UniVO;

@Mapper
public interface UniMapper {

	/**
	 * @param uniVO
	 * @return
	 */
	public int insertUni(UniVO uniVO);
	
	/**
	 * @param memId
	 * @return
	 */
	public List<UniVO> selectUniList(String memId);
	
	/**
	 * @param uniVO
	 * @return
	 */
	public int updateUni(UniVO uniVO);
	
	/**
	 * @param uniNo
	 * @return
	 */
	public int deleteUni(int uniNo);
	
	/**
	 * @param uniNo
	 * @return
	 */
	public UniVO selectUni(int uniNo);
}
