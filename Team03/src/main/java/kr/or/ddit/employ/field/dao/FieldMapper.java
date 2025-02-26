package kr.or.ddit.employ.field.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.vo.FieldVO;

@Mapper
public interface FieldMapper {
	
	/**
	 * 추천공고를 이용한 공고 리스트 조회
	 * @param fielId
	 * @return
	 */
	List<FieldVO> selectRecommendField(@Param("list") List<Integer> fielId);

}
