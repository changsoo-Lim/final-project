package kr.or.ddit.freelancer.freelancer.dao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.annotation.RootContextWebConfig;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.FreelancerVO;

@RootContextWebConfig
@Transactional
class FreelancerMapperTest {
	
	@Autowired
	FreelancerMapper mapper;
	
	FreelancerVO freelancer;
	
	@BeforeEach
	void beforeEach() {
		freelancer = new FreelancerVO();
		freelancer.setMemId("emp001");
		freelancer.setFreeField("test");
		freelancer.setFreeJob("test");;
		freelancer.setFreeStyle("test");
		freelancer.setFreeType("test");
		freelancer.setFreeExperience("N");
		freelancer.setFreeCareer(2);
		freelancer.setFreeDetail("test");
		assertEquals(1, mapper.insertFreelancer(freelancer));
	}
	
	@Test
	void testSelectFreelancer() {
		assertNotNull(mapper.selectFreelancer(freelancer.getMemId()));
	}

	@Disabled
	@Test
	void testSelectFreelancerList() {
		PaginationInfo paging = new PaginationInfo();
		paging.setCurrentPage(1);
//		SimpleCondition simpleCondition = new SimpleCondition();
//		simpleCondition.setSearchWord("은대");
//		paging.setSimpleCondition(simpleCondition);
		assertDoesNotThrow(() -> mapper.selectFreelancerList(paging));
	}
	@Test
	void testUpdateFreelancer() {
		freelancer.setMemId("emp001");
		freelancer.setFreeField("test22");
		freelancer.setFreeJob("test22");;
		freelancer.setFreeStyle("test22");
		freelancer.setFreeType("test2");
		freelancer.setFreeExperience("Y");
		freelancer.setFreeCareer(2);
		freelancer.setFreeDetail("test2");
		assertEquals(1, mapper.updateFreelancer(freelancer));
	}
	@Test
	void testUpdateMemberToFreelancer() {
		assertEquals(1, mapper.updateMemberToFreelancer("emp001"));
	}
	@Test
	void testDeleteFreelancer() {
		assertEquals(1, mapper.deleteFreelancer("emp001"));
	}

}
