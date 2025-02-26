package kr.or.ddit.resume.edu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.EduVO;

@Mapper
public interface EduMapper {

	/**
	 * @param eduVO
	 * @return
	 */
	public int insertEdu(EduVO eduVO);
	
	public List<EduVO> selectEduList(String memId);
	
	public int updateEdu(EduVO eduVO);
	
	public int deleteEdu(int eduNo);

	public EduVO selectEdu(int eduNo);
}
