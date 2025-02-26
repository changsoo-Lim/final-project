package kr.or.ddit.code.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import kr.or.ddit.code.constants.CacheConstants;
import kr.or.ddit.code.dao.CodeMapper;
import kr.or.ddit.vo.CodeVO;

@Service
public class CodeServiceImpl implements CodeService {
	
	@Autowired
	private CodeMapper codeMapper;
	
	/**
     * 부모 코드(CODE_PARENT)를 기준으로 하위 코드를 반환.
     */
    @Override
    @Cacheable(value = CacheConstants.CODES_BY_PARENT, key = "#codeParent")
    public List<CodeVO> selectByParent(String codeParent) {
        return codeMapper.selectByParent(codeParent);
    }

    /**
     * 부모 코드(CODE_PARENT)가 특정 패턴과 유사한 하위 코드 리스트를 반환.
     */
    @Override
    @Cacheable(value = CacheConstants.CODES_BY_PARENT_LIKE, key = "#codeParent")
    public List<CodeVO> selectByParentLike(String codeParent) {
        return codeMapper.selectByParentLike(codeParent);
    }

    /**
     * 부모 코드(CODE_PARENT)가 특정 목록 내에 포함된 하위 코드 리스트를 반환.
     */
    @Override
    @Cacheable(value = CacheConstants.CODES_BY_PARENT_IN, key = "#codeParent")
    public List<CodeVO> selectByParentIn(String codeParent) {
        return codeMapper.selectByParentIn(codeParent);
    }

    /**
     * 부모 코드(CODE_PARENT)의 하위 코드를 서브쿼리로 조회하여 반환.
     */
    @Override
    @Cacheable(value = CacheConstants.CODES_BY_PARENT_SUBQUERY, key = "#codeParent")
    public List<CodeVO> selectByParentInSubquery(String codeParent) {
        return codeMapper.selectByParentInSubquery(codeParent);
    }

    /**
     * 특정 코드(CODE_CD)를 기준으로 단일 코드를 반환.
     */
    @Override
    @Cacheable(value = CacheConstants.CODE_BY_CODE, key = "#codeCd")
    public CodeVO selectCodeByCode(String codeCd) {
        return codeMapper.selectCodeByCode(codeCd);
    }

    /**
     * 코드 값(CODE_CD)이 특정 패턴과 유사한 코드 리스트를 반환.
     */
    @Override
    @Cacheable(value = CacheConstants.CODES_BY_CODE_LIKE, key = "#codeCd")
    public List<CodeVO> selectByCodeCdLike(String codeCd) {
        return codeMapper.selectByCodeCdLike(codeCd);
    }
}
