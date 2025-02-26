package kr.or.ddit.employ.field.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.employ.field.dao.FieldMapper;
import kr.or.ddit.vo.FieldVO;

@Service
public class FieldServiceImpl implements FieldService {
	
	@Inject
	private FieldMapper mapper;
	
	@Override
	public List<FieldVO> selectRecommendField(List<Integer> fielId) {
		return mapper.selectRecommendField(fielId);
	}

}
