package kr.or.ddit.admin.code.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.admin.code.dao.AdminCodeMapper;
import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.vo.CodeVO;

@Service
public class SearchFilterServiceImlp implements SearchFilterService {

	@Inject
	AdminCodeMapper dao;
	
	@Override
	public List<CodeVO> getCode(String code) {
		return dao.getCode(code);
	}
	
	@Override
	public List<CodeVO> readCodeList() {
	    return dao.selectCodeList();
	}
	
	@Override
	public ServiceResult createCode(CodeVO code) {
		if( dao.insertCode(code) > 0) {
			return ServiceResult.OK; 
		}else {
			return	ServiceResult.FAIL;
		}
	}

	@Override
	public CodeVO readCode(String codeCd) {
		return dao.selectCode(codeCd);
	}

	@Override
	public ServiceResult modifyCode(CodeVO code) {
		if( dao.updateCode(code) > 0) {
			return ServiceResult.OK; 
		}else {
			return	ServiceResult.FAIL;
		}
	}

	@Override
	public ServiceResult removeCode(String codeCd) {
		if( dao.deleteCode(codeCd) > 0) {
			return ServiceResult.OK; 
		}else {
			return	ServiceResult.FAIL;
		}
	}

}
