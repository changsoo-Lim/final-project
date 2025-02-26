package kr.or.ddit.users.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.annotation.RootContextWebConfig;
import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.vo.Cert_StatusVO;
import kr.or.ddit.vo.CompanyVO;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.StatusVO;
import kr.or.ddit.vo.UserrollVO;
import kr.or.ddit.vo.UsersVO;
import lombok.extern.slf4j.Slf4j;

@RootContextWebConfig
@Transactional
class UsersServiceImplTest {
		
	@Inject
	UsersService service;
	
	UsersVO user;
	
	@BeforeEach
	void beForeEach() {
		user = new UsersVO();
		user.setUserId("test");
		user.setUserPass("test");
		
		MemberVO member = user.getMemberVO();
		member.setMemId(user.getUserId());
		member.setMemNm("테스트");
		member.setMemGen("M");
		member.setMemRegno("19971015");
		member.setMemZip("12345");
		member.setMemAddr1("서울특별시 강남구");
		member.setMemAddr2("역삼동 12-35");
		member.setMemEmail("test@test.com");
		member.setMemHp("01058103813");
		
		CompanyVO company = user.getCompanyVO();
		company.setCompId(user.getUserId());
		company.setCompNum("999-99-99999");
		company.setCompNm("대덕인재개발원");
		company.setCompRepresentative("임창수");
		company.setCompType("company-size-1"); // 소기업
		company.setCompInd("in01");  // 서비스업
		company.setCompJob("in108");
		company.setCompZip("54321");
		company.setCompAddr1("서울 동작구");
		company.setCompAddr2("창수군");
		company.setCompPhone("0423333333");
		company.setCompEmail("changsu@company.com");
		company.setCompMobile("010-1111-2222");
		company.setCompJobDetail("in10803");
		
		StatusVO status =  user.getStatusVO();
		status.setStatusSms("N");
		status.setStatusEmail("N");
		
		UserrollVO userRole = user.getUserRoleVO();
		userRole.setUserId(user.getUserId());
		
		Cert_StatusVO certStatus = user.getCertStatusVO();
		certStatus.setEmailYn("N");
		certStatus.setHpYn("Y");
	}
	
	
	@Test
	void testCreateMember() {
		user.setUserCd("MEMBER");
		assertEquals(ServiceResult.OK, service.createUser(user));
	}
	
	@Test
	void testCreateComany() {
		user.setUserCd("COMPANY");
		assertEquals(ServiceResult.OK, service.createUser(user));
	}

}
