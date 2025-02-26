package kr.or.ddit.eval.eval.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.eval.eval.dao.EvalMapper;
import kr.or.ddit.vo.EvalVO;

@Service
public class EvalServiceImpl implements EvalService {

	@Inject
	EvalMapper mapper;
	
	@Override
	public ServiceResult createEval(EvalVO evalVO) {
		if(mapper.insertEval(evalVO) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

}
