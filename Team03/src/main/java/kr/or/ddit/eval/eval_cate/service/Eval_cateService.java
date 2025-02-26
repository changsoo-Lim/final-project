package kr.or.ddit.eval.eval_cate.service;

import java.util.List;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.vo.ApplyVO;
import kr.or.ddit.vo.Eval_CateVO;
import kr.or.ddit.vo.FieldVO;

public interface Eval_cateService {
	
	public ServiceResult insertEvalCate(int evalNo, List<Eval_CateVO> evalCates);
	
	public List<ApplyVO> memList(int filedNo);
}
