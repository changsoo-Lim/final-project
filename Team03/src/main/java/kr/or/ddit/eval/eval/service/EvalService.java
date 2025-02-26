package kr.or.ddit.eval.eval.service;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.vo.EvalVO;

public interface EvalService {
	
	public ServiceResult createEval(EvalVO evalVO);
}
