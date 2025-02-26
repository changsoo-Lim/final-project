package kr.or.ddit.code.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.CodeVO;

@Mapper
public interface CodeMapper {
	// ==================== 부모 코드(CODE_PARENT)를 기준으로 하위 코드를 반환 ====================
	
	/**
	 * WHERE CODE_PARENT = #{codeparent}
	 */
	List<CodeVO> selectByParent(String codeparent);
	
	/**
	 * WHERE CODE_PARENT LIKE #{codeparent}
	 */
	List<CodeVO> selectByParentLike(String codeparent);
	
	/**
	 * WHERE CODE_PARENT IN (#{codeparent})
	 */
	List<CodeVO> selectByParentIn(String codeparent);
	
	/**
	 * WHERE CODE_PARENT IN (
		    SELECT CODE_CD
		    FROM CODE
		    WHERE CODE_PARENT LIKE '#{codeparent}'
		)
	 */
	List<CodeVO> selectByParentInSubquery(String codeparent);
	
	
	// ==================== 코드PK(CODE_CD)를 기준으로 코드를 반환 ====================
	
    /**
     * 코드 값을 기준으로 단일 코드를 반환
     */
    CodeVO selectCodeByCode(String codeCd);
    /**
     * WHERE CODE_CD LIKE #{codeCd}
     */
    List<CodeVO> selectByCodeCdLike(String codeCd);
}
