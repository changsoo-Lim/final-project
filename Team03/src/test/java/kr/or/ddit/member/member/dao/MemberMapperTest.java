package kr.or.ddit.member.member.dao;

import static org.junit.jupiter.api.Assertions.*;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import kr.or.ddit.annotation.RootContextWebConfig;
import lombok.extern.slf4j.Slf4j;

@RootContextWebConfig
@Slf4j
class MemberMapperTest {
	@Inject
	MemberMapper dao;

	@Test
	void testLoadUserByUsernameString() {
		fail("Not yet implemented");
	}

	@Test
	void testInsertMember() {
		fail("Not yet implemented");
	}

	@Test
	void testSelectPhoneMember() {
		assertNotNull(dao.selectPhoneMember("01058103813"));
	}

	@Test
	void testSelectMemberForAuth() {
		fail("Not yet implemented");
	}

}
