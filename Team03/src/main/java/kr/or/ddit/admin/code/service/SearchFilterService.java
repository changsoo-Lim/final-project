package kr.or.ddit.admin.code.service;

import java.util.List;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.vo.CodeVO;

public interface SearchFilterService {
	
	public List<CodeVO> getCode(String code);
	
	public List<CodeVO> readCodeList();
	/**
	 * 공통코드 등록
	 * @param code
	 * @return
	 */
	public ServiceResult createCode(CodeVO code);
	/**
	 * 공통 코드 상세조회
	 * @param codeCd
	 * @return
	 */
	public CodeVO readCode(String codeCd);
	/**
	 * 공통코드 수정
	 * @param code
	 * @return
	 */
	public ServiceResult modifyCode(CodeVO code);
	/**
	 * 공통코드 삭제
	 * @param codeCd
	 * @return
	 */
	public ServiceResult removeCode(String codeCd);
	
	
}
