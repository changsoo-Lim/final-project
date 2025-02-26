package kr.or.ddit.employ.employ.dao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.annotation.RootContextWebConfig;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.EmployVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RootContextWebConfig
@Transactional
class EmployMapperTest {
	
	@Autowired
	EmployMapper mapper;
	EmployVO employ;
	
	@BeforeEach
	void beforeEach() {
		employ = new EmployVO();
		employ.setEmployNo(1);
		employ.setCompId("client001");
		employ.setEmployTitle("test");
		employ.setEmployType("test");
		employ.setEmployExperience("test");
		employ.setEmployEducation("test");
		employ.setEmploySalary("test");
		employ.setEmploySalaryYn("Y");
		employ.setEmployEd("2025-01-17");
		employ.setEmployWorkday("test");
		employ.setEmploySwh("test");
		employ.setEmployEwh("test");
		employ.setEmployApplication("test");
		employ.setEmployUrl("test");
		assertEquals(1, mapper.insertEmploy(employ));
	}
	
	@Test
	void testSelectEmploy() {
		PaginationInfo paging = new PaginationInfo();
		paging.setCurrentPage(1);
		assertDoesNotThrow(() -> mapper.selectEmployList(paging, null, null));
		log.info("employ : {}", mapper.selectEmployList(paging, null, null));
	}

	@Test
	void testSelectEmployList() {
		fail("Not yet implemented");
	}

	@Test
	void testInsertEmploy() {
		fail("Not yet implemented");
	}

	@Test
	void testInsertField() {
		fail("Not yet implemented");
	}

	@Test
	void testInsertFilter() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateEmploy() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteEmploy() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteField() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteFilter() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteProcedure() {
		fail("Not yet implemented");
	}

	@Test
	void testIncrementHit() {
		fail("Not yet implemented");
	}

	@Test
	void testSelectFilterTitleList() {
		fail("Not yet implemented");
	}

	@Test
	void testSelectFilterContList() {
		fail("Not yet implemented");
	}

	@Test
	void testSelectMemberTypeList() {
		fail("Not yet implemented");
	}

	@Test
	void testSelectWorkTypeList() {
		fail("Not yet implemented");
	}

	@Test
	void testSelectCityList() {
		fail("Not yet implemented");
	}

	@Test
	void testSelectGunguList() {
		fail("Not yet implemented");
	}

	@Test
	void testSelectSalaryRangeList() {
		fail("Not yet implemented");
	}

	@Test
	void testSelectWorkdayList() {
		fail("Not yet implemented");
	}

	@Test
	void testSelectWorkHourList() {
		fail("Not yet implemented");
	}

	@Test
	void testSelectAppsttusList() {
		fail("Not yet implemented");
	}

	@Test
	void testSelectEducationList() {
		fail("Not yet implemented");
	}

	@Test
	void testSelectAPLCList() {
		fail("Not yet implemented");
	}

}
