package kr.or.ddit.intro.intro.dao;

import static org.junit.jupiter.api.Assertions.fail;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kr.or.ddit.annotation.RootContextWebConfig;
import kr.or.ddit.vo.IntroVO;


@RootContextWebConfig
class IntroMapperTest {
	
	@Inject
	IntroMapper dao;
	
	IntroVO intro;
	
	@BeforeEach
	void beForeEach() {
		IntroVO intro = new IntroVO();
		intro.setIntroNo(1);
		intro.setMemId("1");
		intro.setIntroTitle("1");
		intro.setIntroDel("1");
	}

	@Test
	void testSelectListIntro() {
		
		dao.selectListIntro("emp001");
	}

	@Test
	void testSelectIntro() {
		fail("Not yet implemented");
	}

	@Test
	void testInsertIntro() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateIntro() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteIntro() {
		fail("Not yet implemented");
	}

}
