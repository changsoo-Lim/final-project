package kr.or.ddit.prod.product.dao;

import static org.junit.jupiter.api.Assertions.fail;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.annotation.RootContextWebConfig;
import kr.or.ddit.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RootContextWebConfig
class ProdMapperTest {

	@Inject
	ProdMapper mapper;
	
	private ProductVO prod;
	
	@BeforeEach
	void prod() {
		prod = new ProductVO();
		prod.setProductCd("prod001");
		prod.setProductNm("프레스티지");
		prod.setProductPeriod("1");
		prod.setProductPrice(2000000);
		prod.setProductType("배너");
		prod.setAtchFileNo(null);
		prod.setProductDel("N");
	}
	
	@Test
	void testSelectProdList() {
		
		log.info("{}",mapper.selectProdList());
	}

	@Test
	void testSelectProd() {
		log.info("{}",mapper.selectProd("prod001"));
	}

	@Test
	@Transactional
	void testInsertProd() {
		log.info("{}",mapper.insertProd(prod));
	}

	@Test
	void testUpdateProd() {
		log.info("{}",mapper.updateProd(prod));
	}

	@Test
	void testDeleteProd() {
		log.info("{}",mapper.deleteProd("prod001"));
	}

}
