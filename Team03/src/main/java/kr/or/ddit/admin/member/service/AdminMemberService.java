package kr.or.ddit.admin.member.service;

import java.util.List;

import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.UsersVO;

public interface AdminMemberService {
	
	/**
	 * 어드민 회원 조회
	 * @return
	 */
	public List<MemberVO> getMember();
	
	/**
	 * 어드민 회원 페이징
	 * @param paging
	 * @return
	 */
	public List<UsersVO> readMember(PaginationInfo<UsersVO> paging);

}
