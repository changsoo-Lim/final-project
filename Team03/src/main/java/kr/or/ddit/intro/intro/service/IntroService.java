package kr.or.ddit.intro.intro.service;

import java.util.List;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.vo.IntroVO;

public interface IntroService {

	/**
	 * 마스터 자기소개서 리스트 조회
	 * @param memId
	 * @return
	 */
	public List<IntroVO> readListIntro(String memId);
	
	/**
	 * 마스터 자기소개서 제목 단건 조회
	 * @param introNo
	 * @return
	 */
	public IntroVO readIntro(int introNo);
	
	/**
	 * 마스터 자기소개서 제목 등록
	 * @param introVO
	 * @return
	 */
	public ServiceResult createIntro(IntroVO introVO);
	
	/**
	 * 마스터 자기소개서 제목 수정
	 * @param introVO
	 * @return
	 */
	public ServiceResult modifyIntro(IntroVO introVO);
	
	/**
	 * 삭제여부 변경
	 * @param introNo
	 * @return
	 */
	public ServiceResult removeIntro(String introNo);
	
}
