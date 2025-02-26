package kr.or.ddit.users.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import kr.or.ddit.security.UsersVOWrapper;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.UsersVO;


/**
 * 유저관리용 persistence layer
 *
 */
@Mapper
@Component
public interface UsersMapper extends UserDetailsService {
	@Override
	default UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UsersVO realUser = selectUserForAuth(username);
		
		// 회원이 아닐 경우
		if(realUser==null) {
			throw new UsernameNotFoundException(String.format("%s 사용자 없음.", username));
		}
		// 회원 블랙리스트 / 회원탈퇴 상태 확인		
		if ("Y".equals(realUser.getMemberVO().getMemStatus()) || "B".equals(realUser.getMemberVO().getMemStatus())) {
			throw new UsernameNotFoundException(String.format("%s 일반회원 탈퇴 또는 블랙리스트 상태.", username));
        }
		// 기업 회원탈퇴 상태 확인
		if ("Y".equals(realUser.getCompanyVO().getCompStatus())) {
			throw new UsernameNotFoundException(String.format("%s 기업회원 탈퇴 상태.", username));
        }
		
		return new UsersVOWrapper(realUser);
	}
	
	/**
	 * 유저 등록
	 * @param user
	 * @return 등록된 유저 수
	 */
	public int insertUser(UsersVO user);
	
	/**
	 * 회원 등록
	 * @param user
	 * @return 등록된 회원 수
	 */
	public int insertDetails(UsersVO user);
	
	/**
	 * 사용자 권한 등록
	 * @param user
	 * @return 등록된 사용자 권한 수
	 */
	public int insertUserRole(UsersVO user);
	
	/**
	 * 수신 및 알림여부 등록
	 * @param user
	 * @return 등록된 수신 및 알림여부 수
	 */
	public int insertStatus(UsersVO user);
	
	/**
	 * 회원 인증여부 등록
	 * @param user
	 * @return 등록된 회원의 인증여부 테이블 수 
	 */
	public int insertCertStatus(UsersVO user);
	
	/**
	 * 회원아이디 조회
	 * @param userId
	 * @return UsersVO
	 */
	public UsersVO selectUser(String userId);
	
	/**
	 * 인증용 정보 조회(아이디, 비밀번호, 이름, 이메일, 휴대폰)
	 * @param userId
	 * @return 존재하지 않으면, null 반환
	 */
	public UsersVO selectUserForAuth(String userId);
	
	/**
	 * 비밀번호 재설정 
	 * @param user
	 * @return 성공 1 / 실패 0
	 */
	public int updatePassword(UsersVO user);
	
	/**
	 * 성별코드 조회
	 * @return CodeVO 
	 */
	public List<CodeVO> selectCodeGender();
	
	/**
	 * 기업구분 조회
	 * @return CodeVO
	 */
	public List<CodeVO> selectCompanySizeCode();
	
	/**
	 * 직종 / 직무 / 직무상세 조회
	 * @return CodeVO
	 */
	public List<CodeVO> selectIndustryCode();
	
	/**
	 * 회원 비밀번호 확인
	 * @param user 회원VO
	 * @return 성공 1 / 실패 0
	 */
	public int selectPassCheck(UsersVO user);
	
	/**
	 * 회원 비밀번호 조회
	 * @param userId 회원 아이디
	 * @return UsersVO 
	 */
	public UsersVO selectPass(String userId);
	
	/**
	 * 회원 삭제
	 * @param user 회원VO
	 * @return 성공 1 : 실패 0
	 */
	public int deleteUser(UsersVO user);
	
}
