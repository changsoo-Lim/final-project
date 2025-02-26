package kr.or.ddit.resume.career.service;

import java.util.List;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.vo.CareerVO;

public interface CareerService {
	/**
	 * 경력사항 등록
	 * @param careerVO
	 * @return
	 */
	public ServiceResult createCareer(CareerVO careerVO);
	
	/**
	 * 경력사항 조회
	 * @param memId
	 * @return
	 */
	public List<CareerVO> readCareerList(String memId);
	
	/**
	 * 경력사항 수정
	 * @param careerVO
	 * @return
	 */
	public ServiceResult modifiyCareer(CareerVO careerVO);
	
	public ServiceResult removeCareer(int careerNo);
	
	public CareerVO readCareer(int careerNo);
}
