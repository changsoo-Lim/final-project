package kr.or.ddit.prod.buy.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.annotation.RootContextWebConfig;
import kr.or.ddit.vo.BuyVO;
import kr.or.ddit.vo.CompanyVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RootContextWebConfig
class BuyMapperTest {
	
	@Inject
	BuyMapper mapper;
	
	BuyVO buy;
	
	@BeforeEach
	void buy() {
		CompanyVO company = new CompanyVO();
		company.setCompId("client001");
		
		
		
		buy = new BuyVO();
		buy.setBuyNo(1);
		buy.setProductCd("prod20241226000005");
		buy.setEmployNo(3);
		buy.setCompId(company.getCompId());
		buy.setBuyAmount(5000000);
		buy.setBuyMethod("MAIN");
		buy.setBuyDate(LocalDate.now());
		buy.setBuyQty(5);
		buy.setBuySdt("20241230");
		buy.setBuyEdt("20240105");
		buy.setAtchFileNo(null);

	}

	@Test
	void testSelectBuyList() {
		buy.setCompId("comp001");
		log.info("{}",mapper.selectBuyList(buy.getCompId()));
	}

	@Test
	void testSelectBuy() {
		buy.setBuyNo(1);
		log.info("{}",mapper.selectBuy(buy.getBuyNo()));
	}

	@Test
	@Transactional
	void testInsertBuy() {
		log.info("{}",mapper.insertBuy(buy));
	}

}
