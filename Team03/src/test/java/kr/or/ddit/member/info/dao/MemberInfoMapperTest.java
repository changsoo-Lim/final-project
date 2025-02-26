package kr.or.ddit.member.info.dao;

import static org.junit.jupiter.api.Assertions.*;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.annotation.RootContextWebConfig;
import kr.or.ddit.vo.Cert_StatusVO;
import kr.or.ddit.vo.CompanyVO;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.StatusVO;
import kr.or.ddit.vo.UserrollVO;
import kr.or.ddit.vo.UsersVO;
import lombok.extern.slf4j.Slf4j;

@RootContextWebConfig
@Transactional
@Slf4j
class MemberInfoMapperTest {
	
	@Inject
	MemberInfoMapper mapper;
	
	MemberVO member;
	
	@BeforeEach
	void beForeEach() {
		member = new MemberVO();
		member.setMemId("ohyungeun");
		member.setMemNm("연근오");
		member.setMemGen("F");
		member.setMemRegno("19991111");
		member.setMemZip("33333");
		member.setMemAddr1("대전 서구 오류동");
		member.setMemAddr2("대덕인재개발원");
		member.setMemEmail("1234@naver.com");
		member.setMemHp("01011112222");
		member.setMemUrl("https://www.ddit.or.kr/");
		member.setMemDateType("dt02");
		
		Cert_StatusVO certStatus =  new Cert_StatusVO();
		certStatus.setEmailYn("Y");
		certStatus.setHpYn("Y");
		
		member.setCertStatus(certStatus);
	}
	
	
	@Test
	void testSelectMemberCertStatus() {
		assertNotNull(mapper.selectMemberCertStatus(member.getMemId()), "성공이오");
	}
	
	@Test
	void testSelectMemberInfoCode() {
		assertNotNull(mapper.selectMemberInfoCode(), "성공이오");
	}
	
	@Test
	void testSelectMemberInfo() {
		assertNotNull(mapper.selectMemberInfo(member.getMemId()), "성공이오");
	}
	
	@Test
	void testUpdateMemberInfo() {
		assertEquals(1, mapper.updateMemberInfo(member));
	}
	
	@Test
	void testSelectMemberEmailCheck() {
		// 중복체크
		member.setMemEmail("stackup406@naver.com");
		assertEquals(1, mapper.selectMemberEmailCheck(member.getMemEmail()));
	}
	
	@Test
	void testSelectMemberPhoneCheck() {
		// 중복체크
		member.setMemHp("01058103813");
		assertEquals(1, mapper.selectMemberPhoneCheck(member.getMemHp()));
	}
}
