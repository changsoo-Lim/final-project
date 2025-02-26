package kr.or.ddit.member.info.service;

import java.util.List;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.vo.Cert_StatusVO;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.MemberVO;

public interface MemberInfoService {
	/**
	 * 회원정보 수정에 필요한 공통코드 조회
	 * @return CodeVO 생년월일 코드, 성별코드
	 */
	public List<CodeVO> readMemberInfoCodeList();
	
	/**
	 * 회원의 인증여부 정보 조회 메소드
	 * @param userId 회원아이디
	 * @return Cert_StatusVO 회원의 인증여부 VO
	 */
	public Cert_StatusVO readMemberCertStatus(String userId);
	
	
	/**
	 * 회원정보 조회
	 * @param userId 회원아이디
	 * @return MemberVO 회원정보 VO
	 */
	public MemberVO readMemberInfo(String userId);
	
	/**
	 * 회원의 이메일 중복 여부 확인
	 * @param memEmail 
	 * @return 중복O : ok / 중복X fail
	 */
	public String readMemberEmailCheck(String memEmail);
	
	/**
	 * 휴대폰 중복 여부 확인
	 * @param memHp 
	 * @return 중복O : ok / 중복X fail
	 */
	public String readMemberPhoneCheck(String memHp);
	
	/**
	 * 회원정보 업데이트
	 * @param member 회원 VO
	 * @return 성공 : OK / 실패 : FAIL
	 */
	public ServiceResult modifyMemberInfo(MemberVO member);
}
