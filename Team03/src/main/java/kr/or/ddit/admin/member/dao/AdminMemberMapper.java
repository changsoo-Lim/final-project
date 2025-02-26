package kr.or.ddit.admin.member.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.UsersVO;

@Mapper
public interface AdminMemberMapper {
	
	/**
	 * 어드민 회원 조회
	 * @return
	 */
	public List<MemberVO> selectMember();
	
	/**
	 * 토탈 레코드
	 * @param paging
	 * @return
	 */
	public int selectTotalRecord(PaginationInfo<UsersVO> paging);
	
	/**
	 * 어드민 회원 페이징
	 * @param paging
	 * @return
	 */
	public List<UsersVO> selectMemberList(@Param("paging") PaginationInfo<UsersVO> paging);

}
