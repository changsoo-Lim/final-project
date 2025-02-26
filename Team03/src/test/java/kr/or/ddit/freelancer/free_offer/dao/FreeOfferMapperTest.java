package kr.or.ddit.freelancer.free_offer.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.annotation.RootContextWebConfig;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.Free_OfferVO;

@RootContextWebConfig
@Transactional
class FreeOfferMapperTest {
	@Autowired
	FreeOfferMapper mapper;
	
	Free_OfferVO freeOffer;
	
	@BeforeEach
	void beforeEach() {
		freeOffer = new Free_OfferVO();
		freeOffer.setMemId("emp005");
		freeOffer.setProjectNo(2);
		assertEquals(1, mapper.insertFreeOffer(freeOffer));
	}

	@Test
	void testMemSelectFreeOfferList() {
		PaginationInfo paging = new PaginationInfo();
		paging.setCurrentPage(1);
		assertNotNull(mapper.memSelectFreeOfferList(paging, "emp005"));
	}

	@Test
	void testCompSelectFreeOfferList() {
		PaginationInfo paging = new PaginationInfo();
		paging.setCurrentPage(1);
		assertNotNull(mapper.compSelectFreeOfferList(paging, "client001"));
	}

	@Test
	void testUpdateFreeOffer() {
		freeOffer = new Free_OfferVO();
		freeOffer.setMemId("emp005");
		freeOffer.setProjectNo(2);
		freeOffer.setFreeOfferStatus("OF03");
		assertEquals(1, mapper.updateFreeOffer(freeOffer));
	}

}
