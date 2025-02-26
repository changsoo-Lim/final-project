package kr.or.ddit.member.info.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.annotation.RootContextWebConfig;
import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.vo.Cert_StatusVO;
import kr.or.ddit.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@RootContextWebConfig
class MemberInfoServiceImplTest {

	@Inject
	MemberInfoService service;
	
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
	void testModifyMemberInfo() {
		assertEquals(ServiceResult.OK, service.modifyMemberInfo(member));
	}

}
