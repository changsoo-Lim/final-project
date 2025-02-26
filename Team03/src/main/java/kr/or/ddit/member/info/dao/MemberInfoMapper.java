package kr.or.ddit.member.info.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.Cert_StatusVO;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.MemberVO;

@Mapper
public interface MemberInfoMapper {
	
	/**
	 * 회원정보 수정에 필요한 공통코드 조회
	 * @return CodeVO 생년월일 코드, 성별코드
	 */
	public List<CodeVO> selectMemberInfoCode();
	
	/**
	 * 회원의 인증여부 정보 조회 메소드
	 * @param userId 회원아이디
	 * @return Cert_StatusVO 회원의 인증여부 VO
	 */
	public Cert_StatusVO selectMemberCertStatus(String userId);
	
	
	/**
	 * 회원정보 조회
	 * @param userId 회원아이디
	 * @return MemberVO 회원정보 VO
	 */
	public MemberVO selectMemberInfo(String userId);
	
	/**
	 * 이메일 중복 여부 확인
	 * @param memEmail 
	 * @return 중복O : 1 / 중복X 0
	 */
	public int selectMemberEmailCheck(String memEmail);
	
	/**
	 * 휴대폰 중복 여부 확인
	 * @param memHp 
	 * @return 중복O : 1 / 중복X 0
	 */
	public int selectMemberPhoneCheck(String memHp);
	
	/**
	 * 회원정보 업데이트
	 * @param member 회원 VO
	 * @return 성공 : 1 / 실패 : 0
	 */
	public int updateMemberInfo(MemberVO member);
	
	/**
	 * 회원의 이메일/휴대폰 인증여부 업데이트
	 * @param member 회원 VO
	 * @return 성공 : 1 / 실패 : 0
	 */
	public int updateMemberCertstat(MemberVO member);
}