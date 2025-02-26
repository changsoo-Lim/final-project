package kr.or.ddit.eval.eval_cate.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.eval.eval_cate.dao.Eval_cateMapper;
import kr.or.ddit.vo.ApplyVO;
import kr.or.ddit.vo.Eval_CateVO;

@Service
public class Eval_cateServiceImpl implements Eval_cateService {

	@Inject
	Eval_cateMapper mapper;
	
	@Override
	@Transactional
	public ServiceResult insertEvalCate(int evalNo, List<Eval_CateVO> evalCates) {
		List<ServiceResult> resultList = new ArrayList<>();
	    for (Eval_CateVO evalCate : evalCates) {
	    	evalCate.setEvalNo(evalNo); 
	        if(mapper.insertEvalCate(evalCate) >0) {
	        	resultList.add(ServiceResult.OK);
	        }else {
	        	resultList.add(ServiceResult.FAIL);
	        }

	    }
	    return resultList.contains(ServiceResult.FAIL) ? ServiceResult.FAIL : ServiceResult.OK;
	}

	@Override
	public List<ApplyVO> memList(int filedNo) {
		return mapper.memList(filedNo);
	}




}
