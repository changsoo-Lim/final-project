package kr.or.ddit.users.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.annotation.RootContextWebConfig;
import kr.or.ddit.vo.Cert_StatusVO;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.CompanyVO;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.StatusVO;
import kr.or.ddit.vo.UserrollVO;
import kr.or.ddit.vo.UsersVO;
import lombok.extern.slf4j.Slf4j;

@RootContextWebConfig
@Slf4j
@Transactional
class UsersMapperTest {
	
	@Inject
	UsersMapper mapper;
	@Inject
	PasswordEncoder encoder;
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
		company.setCompType("대기업");
		company.setCompInd("IT기업");
		//company.setCompIndDetail("소프트웨어");
		company.setCompZip("54321");
		company.setCompAddr1("서울 동작구");
		company.setCompAddr2("창수군");
		company.setCompPhone("0423333333");
		company.setCompEmail("changsu@company.com");
		company.setCompMobile("010-1111-2222");
		
		StatusVO status =  user.getStatusVO();
		status.setStatusSms("N");
		status.setStatusEmail("N");
		
		//Cert_StatusVO certStatus = user.getCertStatusVO();
		
		
		UserrollVO userRole = user.getUserRoleVO();
		userRole.setUserId(user.getUserId());
	}
	
	@Test
	void testUpdatePassword() {
		UsersVO user1 = mapper.selectUser("ohyungeun");
		
		user1.setUserPass(encoder.encode("12345"));
		
		assertEquals(1, mapper.updatePassword(user1));
	}
	
	@Test
	void testInsertCertStatus() {
		log.info(String.valueOf(mapper.insertCertStatus(user)));
	}
	
	@Test
	void testSelectCodeGender() {
		assertNotNull(mapper.selectCodeGender(), "성공");
	}
	
	@Test
	void testSelectCompanySizeCode() {
		assertNotNull(mapper.selectCompanySizeCode(), "성공");
	}
	
	@Test
	void testSelectIndustry() {
		assertNotNull(mapper.selectIndustryCode(), "성공");
	}
	
	
	@Test
	void testSelectPass() {
		UsersVO user2 = mapper.selectPass("ohyungeun");
		
		String userPass = "java";
		String userPassFromDB = user2.getUserPass();
		
		log.info(userPass);
		log.info(userPassFromDB);
		
		assertTrue(encoder.matches(userPass, userPassFromDB));
	}
	
	
	@Test
	void testDeleteUser() {
		UsersVO user3 = new UsersVO();
		user3.setUserId("ohyungeun");
		user3.setUserCd("ROLE02");
		
		assertEquals(1, mapper.deleteUser(user3));
	}
	

}
