package kr.or.ddit.intro.intro.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.IntroVO;

@Mapper
public interface IntroMapper {

	/**
	 * 마스터 자기소개서 리스트 조회
	 * @param memId
	 * @return
	 */
	public List<IntroVO> selectListIntro(String memId);
	
	/**
	 * 마스터 자기소개서 제목 단건 조회
	 * @param introNo
	 * @return
	 */
	public IntroVO selectIntro(int introNo);
	
	/**
	 * 마스터 자기소개서 제목 등록
	 * @param introVO
	 * @return
	 */
	public int insertIntro(IntroVO introVO);
	
	/**
	 * 마스터 자기소개서 제목 수정
	 * @param introVO
	 * @return
	 */
	public int updateIntro(IntroVO introVO);
	
	/**
	 * 삭제여부 변경
	 * @param introNo
	 * @return
	 */
	public int deleteIntro(String introNo);
}
