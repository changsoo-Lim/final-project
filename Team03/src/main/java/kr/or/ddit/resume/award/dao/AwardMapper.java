package kr.or.ddit.resume.award.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.AwardVO;

@Mapper
public interface AwardMapper {
	
	/**
	 * @param awardVO
	 * @return
	 */
	public int insertAward(AwardVO awardVO);
	
	public List<AwardVO> selectAwardList(String memId);
	
	public int updateAward(AwardVO awardVO);

	public int deleteAward(int awardNo);

	public AwardVO selectAward(int awardNo);
	
}
