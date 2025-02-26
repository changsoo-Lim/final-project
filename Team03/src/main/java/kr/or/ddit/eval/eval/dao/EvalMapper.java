package kr.or.ddit.eval.eval.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.EvalVO;

@Mapper
public interface EvalMapper {
	
	public int insertEval(EvalVO evalVO);
	
}
