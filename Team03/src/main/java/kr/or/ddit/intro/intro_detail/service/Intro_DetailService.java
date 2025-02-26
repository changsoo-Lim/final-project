package kr.or.ddit.intro.intro_detail.service;

import java.util.List;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.vo.Intro_DetailVO;

public interface Intro_DetailService {
	
	/**
	 * @param introDetailVO
	 * @return
	 */
	public Intro_DetailVO readIntroDetail(Intro_DetailVO introDetailVO);
	
	

	/**
	 * @param introNo
	 * @return
	 */
	public List<Intro_DetailVO> readListIntroDetail(int introNo);
	
	
	/**
	 * @param introDetailVO
	 * @return
	 */
	public ServiceResult createIntroDetail(Intro_DetailVO introDetailVO);
	
	/**
	 * @param introDetailVO
	 * @return
	 */
	public ServiceResult modifyIntroDetail(Intro_DetailVO introDetailVO);
	
	/**
	 * @param introDetailNo
	 * @return
	 */
	public ServiceResult removeIntroDetail(int introDetailNo);



	
	
}
