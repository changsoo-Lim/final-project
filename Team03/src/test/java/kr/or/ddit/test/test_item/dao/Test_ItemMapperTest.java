package kr.or.ddit.test.test_item.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.annotation.RootContextWebConfig;
import kr.or.ddit.vo.TestItemVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@RootContextWebConfig
class Test_ItemMapperTest {

	@Inject
	Test_ItemMapper mapper;
	
	TestItemVO testItem;
	
	@BeforeEach
    void BeforeEach() {
		testItem = new TestItemVO();
		testItem.setItemCont("지문test1");
		testItem.setItemScore(10);
		testItem.setItemYn("Y");
		testItem.setQueType("QT01");
		testItem.setQueNo(1);
		testItem.setAtchFileNo(9);
        assertEquals(1, mapper.insertTestItem(testItem));
    }
	
	@Test
	void testSelectQustionTypeCode() {
		assertNotNull(mapper.selectQustionTypeCode(), "★★★★★★★★★★★★ 테스트 코드조회");
	}

	@Test
	void testInsertTestItem() {
		testItem = new TestItemVO();
		testItem.setItemCont(null);
		testItem.setItemScore(null);
		testItem.setItemYn(null);
		testItem.setQueType("QT02");
		testItem.setQueNo(2);
		testItem.setAtchFileNo(9);
        assertEquals(1, mapper.insertTestItem(testItem));
	}

	@Test
	void testSelectTestItemList() {
		int queNo = testItem.getQueNo();
		assertNotNull(mapper.selectTestItemList(queNo), "★★★★★★★★★★★★ 테스트 코드조회");
	}

	@Test
	void testUpdateTestItem() {
		testItem.setItemCont("testest");
		testItem.setItemScore(30);
		testItem.setItemYn("N");
		testItem.setQueType("QT01");
		testItem.setQueNo(3);
		testItem.setAtchFileNo(9);
		assertEquals(1, mapper.updateTestItem(testItem));
	}

	@Test
	void testDeleteTestItem() {
		int itemNo = testItem.getItemNo();
		assertEquals(1, mapper.deleteTestItem(itemNo));
	}

}
