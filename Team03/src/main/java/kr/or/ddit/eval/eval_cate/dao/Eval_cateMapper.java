package kr.or.ddit.eval.eval_cate.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.ApplyVO;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.Eval_CateVO;

@Mapper
public interface Eval_cateMapper {
	
	public List<CodeVO> interviewList();
	
	public int insertEvalCate(Eval_CateVO evalCateVO);
	
	public List<ApplyVO> memList(int filedNo);
}
