package kr.or.ddit.users.service;

import java.util.List;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.UsersVO;

/**
 * 회원가입 Service Interface
 */
public interface UsersService {
	
	/**
	 * 회원가입 메소드 
	 * @param usersVO
	 * @return OK, FAIL, PKDUPLICATED
	 */
	public ServiceResult createUser(UsersVO usersVO);
	
	/**
	 * 회원 정보 찾기 단건 조회
	 * @param userId
	 * @return UsersVO
	 */
	public UsersVO readUser(String userId);
	
	/**
	 * 비밀번호 재설정 
	 * @param user
	 * @return OK, FAIL
	 */
	public ServiceResult modifyPassword(UsersVO user);
	
	/**
	 * 성별코드 조회
	 * @return CodeVO 
	 */
	public List<CodeVO> readCodeGender();
	
	/**
	 * 기업구분 조회
	 * @return CodeVO
	 */
	public List<CodeVO> readCompanySizeCode();
	
	/**
	 * 직종 / 직무 / 직무상세 조회
	 * @return CodeVO
	 */
	public List<CodeVO> readIndustryCode();
	
	/**
	 * 회원 비밀번호 조회
	 * @param user 회원
	 * @return UsersVO
	 */
	public UsersVO readPass(String userId);
	
	/**
	 * 회원의 비밀번호와 입력한 비밀번호 일치여부파악
	 * @param rawPassword 입력한 비밀번호
	 * @param encodedPassword 회원의 비밀번호
	 * @return boolean
	 */
	public boolean checkPassword(String rawPassword, String encodedPassword);
	
	/**
	 * 회원 삭제
	 * @param user 회원VO
	 * @return OK, FAIL
	 */
	public ServiceResult removeUser(UsersVO user);
	
}
