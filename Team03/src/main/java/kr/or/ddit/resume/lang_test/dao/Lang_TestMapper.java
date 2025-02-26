package kr.or.ddit.resume.lang_test.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.Lang_TestVO;

@Mapper
public interface Lang_TestMapper {
	
	/**
	 * @param langTestVO
	 * @return
	 */
	public int insertLangTest(Lang_TestVO langTestVO);
	
	public List<Lang_TestVO> selectLangtestList(String memId);
	
	public int updateLangTest(Lang_TestVO langTestVO);

	public int deleteLangTest(int langTestNo);

	public Lang_TestVO selectLangtest(int langTestNo);
}
