package kr.or.ddit.test.test.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.test.test.dao.TestMapper;
import kr.or.ddit.test.test.vo.TestVOWrapper;
import kr.or.ddit.test.test_answer.dao.Test_AnswerMapper;
import kr.or.ddit.test.test_item.dao.Test_ItemMapper;
import kr.or.ddit.test.test_keyword.dao.Test_keywordMapper;
import kr.or.ddit.test.test_question.dao.Test_QuestionMapper;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.TestItemVO;
import kr.or.ddit.vo.TestVO;
import kr.or.ddit.vo.Test_KeywordVO;
import kr.or.ddit.vo.Test_QuestnVO;

@Service
public class TestServiceImpl implements TestService {
	
	@Inject
	TestMapper testMapper;
	
	@Inject
	Test_QuestionMapper testQuestionMapper;
	@Inject
	Test_AnswerMapper testAnwserMapper;
	@Inject
	Test_ItemMapper testItemMapper;
	@Inject
	Test_keywordMapper testKeywordMapper;

	@Override
	public List<CodeVO> readTestCode() {
		List<CodeVO> testCode = testMapper.selectTestCode();
		if (testCode == null) {
	        throw new IllegalStateException("해당 테스트 코드가 없습니다.");
	    }
		return testCode;
	}

	@Override
	@Transactional
	public ServiceResult createTest(TestVOWrapper testVOWrapper) {
		ServiceResult result = ServiceResult.FAIL;
		Map<String, ServiceResult> resultMap = new HashMap<>();
		// test 등록
		if(testMapper.insertTest(testVOWrapper.getTest()) > 0 ) {
			result = ServiceResult.OK;
		} else {
			result = ServiceResult.FAIL;
		}
		resultMap.put("test", result);
		// 문제 등록
		for (Test_QuestnVO question : testVOWrapper.getTestQuestnList()) {
            question.setTestNo(testVOWrapper.getTest().getTestNo());
            if(testQuestionMapper.insertTestQuestion(question) > 0 ) {
    			result = ServiceResult.OK;
    		} else {
    			result = ServiceResult.FAIL;
    		}
            resultMap.put("testQuestion", result);
            // 지문 등록
            for (TestItemVO item : question.getItemList()) {
                item.setQueNo(question.getQueNo());
                
                if(testItemMapper.insertTestItem(item) > 0 ) {
        			result = ServiceResult.OK;
        		} else {
        			result = ServiceResult.FAIL;
        		}
                resultMap.put("testItem", result);

                // 주관식 처리: itemYn == "N"인 경우 키워드 저장
                if ("N".equals(item.getItemYn()) && item.getKeywordList() != null) {
                    for (Test_KeywordVO keyword : item.getKeywordList()) {
                        keyword.setItemNo(item.getItemNo());
                        if(testKeywordMapper.insertTestKeyword(keyword) > 0 ) {
                			result = ServiceResult.OK;
                		} else {
                			result = ServiceResult.FAIL;
                		}
                        resultMap.put("testItem", result);
                    }
                }
            }
        }
		
		// 모든 등록이 완료되면 ServiceResult 확인 후 result값 반환 (성공 : OK, 실패 : FAIL)
		if(resultMap.containsValue(ServiceResult.FAIL)) {
			result = ServiceResult.FAIL;
		} else {
			result = ServiceResult.OK;
		}
		
		return result;
	}

	@Override
	public List<TestVO> readTestList(String testCd) {
		List<TestVO> testList = testMapper.selectTestList(testCd);
		if (testList == null) {
	        throw new IllegalStateException("테스트 리스트가 없습니다.");
	    }
		return testList;
	}

	@Override
	public TestVO readTest(int testNo) {
		TestVO test = testMapper.selectTest(testNo);
		if (test == null) {
	        throw new IllegalStateException("해당 테스트가 없습니다.");
	    }
		return test;
	}

	@Override
	public ServiceResult modifyTest(TestVOWrapper testVOWrapper) {
		ServiceResult result = null;
		Map<String, ServiceResult> resultMap = new HashMap<>();
		
		if(testMapper.updateTest(testVOWrapper.getTest()) > 0 ) {
			result = ServiceResult.OK;
		} else {
			result = ServiceResult.FAIL;
		}
		resultMap.put("testItem", result);
	    // 문제 업데이트
	    for (Test_QuestnVO question : testVOWrapper.getTestQuestnList()) {
	        if(testQuestionMapper.updateTestQuestion(question) > 0 ) {
				result = ServiceResult.OK;
			} else {
				result = ServiceResult.FAIL;
			}
	        resultMap.put("testItem", result);
	        // 지문 업데이트
	        for (TestItemVO item : question.getItemList()) {
	            if(testItemMapper.updateTestItem(item) > 0 ) {
					result = ServiceResult.OK;
				} else {
					result = ServiceResult.FAIL;
				}
	            resultMap.put("testItem", result);
	            // 주관식 지문일 경우 키워드 업데이트
	            if ("N".equals(item.getItemYn()) && item.getKeywordList() != null) {
	                for (Test_KeywordVO keyword : item.getKeywordList()) {
	                    if(testKeywordMapper.updateTestKeyword(keyword) > 0 ) {
	    					result = ServiceResult.OK;
	    				} else {
	    					result = ServiceResult.FAIL;
	    				}
	                    resultMap.put("testItem", result);
	                }
	            }
	        }
	    }
	    // 모든 등록이 완료되면 ServiceResult 확인 후 result값 반환 (성공 : OK, 실패 : FAIL)
 		if(resultMap.containsValue(ServiceResult.FAIL)) {
 			result = ServiceResult.FAIL;
 		} else {
 			result = ServiceResult.OK;
 		}
 		
		return result;
	}

	@Override
	public ServiceResult removeTest(int testNo) {
		ServiceResult result = null;
		if(testMapper.deleteTest(testNo) > 0 ) {
			result = ServiceResult.OK;
		} else {
			result = ServiceResult.FAIL;
		}
		return result;
	}

}
