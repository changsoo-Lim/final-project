package kr.or.ddit.member.member.dao;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.UsersVO;

@Mapper
public interface MemberMapper  {
	/**
	 * 회원등록
	 * @param member
	 * @return 등록된 회원수
	 */
	public int insertMember(MemberVO member);
	
	/**
	 * 핸드폰번호로 회원 조회
	 * @param memHp
	 * @return MemberVO 등록된 회원VO
	 */
	public MemberVO selectPhoneMember(String memHp);
	
	
	/**
	 * 파이썬으로 공고를 추천할 회원 정보
	 * @param memId 
	 * @return
	 */
	public MemberVO selectMember(String memId);
	
}
