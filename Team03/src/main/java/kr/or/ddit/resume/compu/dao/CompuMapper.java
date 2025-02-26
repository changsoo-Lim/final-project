package kr.or.ddit.resume.compu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.CompVO;

@Mapper
public interface CompuMapper {

	/**
	 * @param compVO
	 * @return
	 */
	public int insertComp(CompVO compVO);
	
	public List<CompVO> selectCompList(String memId);
	
	public int updateComp(CompVO compVO);
	
	public int deleteComp(int compNo);
	
	public CompVO selectComp(int compNo);
}
