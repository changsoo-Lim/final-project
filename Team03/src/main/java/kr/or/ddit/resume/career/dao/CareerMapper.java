package kr.or.ddit.resume.career.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.CareerVO;

@Mapper
public interface CareerMapper {
	
	/**
	 * 경력사항 등록
	 * @param careerVO
	 * @return
	 */
	public int insertCareer(CareerVO careerVO);
	
	/**
	 * 경력사항 조회
	 * @param memId
	 * @return
	 */
	public List<CareerVO> selectCareerList(String memId);
	
	/**
	 * 경력사항 수정
	 * @param careerVO
	 * @return
	 */
	public int updateCareer(CareerVO careerVO);
	
	public int deleteCareer(int careerNo);
	
	public CareerVO selectCareer(int careerNo);
}
