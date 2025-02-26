package kr.or.ddit.intro.intro_detail.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.Intro_DetailVO;


@Mapper
public interface Intro_DetailMapper {
	
	/**
	 * @param introDetailVO
	 * @return
	 */
	public Intro_DetailVO selectIntroDetail(Intro_DetailVO introDetailVO);
	

	/**
	 * @param introNo
	 * @return
	 */
	public List<Intro_DetailVO> selectListIntroDetail(int introNo);
	
	/**
	 * @param introDetailVO
	 * @return
	 */
	public int insertIntroDetail(Intro_DetailVO introDetailVO);
	
	/**
	 * @param introDetailVO
	 * @return
	 */
	public int updateIntroDetail(Intro_DetailVO introDetailVO);
	
	/**
	 * @param introDetailNo
	 * @return
	 */
	public int deleteIntroDetail(int introDetailNo);
	
}
