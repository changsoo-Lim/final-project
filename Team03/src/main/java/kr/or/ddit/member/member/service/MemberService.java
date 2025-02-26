package kr.or.ddit.member.member.service;

public interface MemberService {
	/**
	 * 핸드폰번호로 회원 조회
	 * @param memHp
	 * @return String 유저 아이디
	 */
	public String readPhoneMember(String memHp);
	
}
