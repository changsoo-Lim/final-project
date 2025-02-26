package kr.or.ddit.admin.code.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.vo.CodeVO;

@Mapper
public interface AdminCodeMapper {

	public List<CodeVO> getCode(@Param("code") String code);
	
	public List<CodeVO> selectCodeList();
	
	public int insertCode(CodeVO code);
	public CodeVO selectCode(String codeCd);
	public int updateCode(CodeVO code);
	public int deleteCode(String codeCd);

}
