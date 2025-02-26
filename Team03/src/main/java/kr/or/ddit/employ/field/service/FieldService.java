package kr.or.ddit.employ.field.service;

import java.util.List;

import kr.or.ddit.vo.FieldVO;

public interface FieldService {
	
	/**
	 * 추천공고 로 검색한 공고 리스트
	 * @param fielId
	 * @return
	 */
	public List<FieldVO> selectRecommendField(List<Integer> fielId);
}
