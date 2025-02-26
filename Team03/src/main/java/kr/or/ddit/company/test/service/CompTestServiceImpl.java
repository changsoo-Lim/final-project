package kr.or.ddit.company.test.service;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.company.test.dao.CompTestMapper;
import kr.or.ddit.file.service.BaseService;
import kr.or.ddit.file.service.FileService;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.test.test.vo.TestVOWrapper;
import kr.or.ddit.test.test_answer.dao.Test_AnswerMapper;
import kr.or.ddit.test.test_item.dao.Test_ItemMapper;
import kr.or.ddit.test.test_keyword.dao.Test_keywordMapper;
import kr.or.ddit.test.test_question.dao.Test_QuestionMapper;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.FileVO;
import kr.or.ddit.vo.File_DetailVO;
import kr.or.ddit.vo.TestItemVO;
import kr.or.ddit.vo.TestVO;
import kr.or.ddit.vo.Test_KeywordVO;
import kr.or.ddit.vo.Test_QuestnVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("compTestService")
public class CompTestServiceImpl implements CompTestService, BaseService {

	@Inject
	CompTestMapper testMapper;
	
	@Inject
	Test_QuestionMapper testQuestionMapper;
	@Inject
	Test_AnswerMapper testAnwserMapper;
	@Inject
	Test_ItemMapper testItemMapper;
	@Inject
	Test_keywordMapper testKeywordMapper;
	@Inject
	FileService fileService;
	
	@Value("#{dirInfo.saveDirCompTest}")
	private Resource saveFolderRes;
	private File saveFolder;
	
	@PostConstruct
	public void init() throws IOException {
		this.saveFolder = saveFolderRes.getFile();
		if(!this.saveFolder.exists()) {
			this.saveFolder.mkdirs();
		}
	}

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
		log.info("testVOWrappper : " + testVOWrapper.toString());
		// 0번 인덱스 null 문제 제거
		List<Test_QuestnVO> Questions = testVOWrapper.getTestQuestnList().stream()
					            .filter(question -> question.getQueCont() != null && !question.getQueCont().trim().isEmpty()) // 유효한 문제만 포함
					            .collect(Collectors.toList());
		
		ServiceResult result = ServiceResult.FAIL;
		Map<String, ServiceResult> resultMap = new HashMap<>();
		// test 등록
		if( testMapper.insertTest(testVOWrapper.getTest()) > 0 ) {
			result = ServiceResult.OK;
		} else {
			result = ServiceResult.FAIL;
		}
		resultMap.put("test", result);
		
		// 문제 등록
		String testCd = testVOWrapper.getTest().getTestCd();
		int questionIndex = 0;
		for (Test_QuestnVO question : Questions) {
			questionIndex++;
        	log.info("★★★★★★★★★★★★★★★★★★★★★★★★★");
        	log.info(question.toString());
        	log.info("★★★★★★★★★★★★★★★★★★★★★★★★★");
            question.setTestNo(testVOWrapper.getTest().getTestNo());
            // 인성검사 였을 시에 문제 점수 0점 처리
            if("TE01".equals(testCd)) {
            	question.setQueScore(0);
            }
            Integer questionAtchFileNo = Optional.ofNullable(question.getFile())
    				.filter(af->! CollectionUtils.isEmpty(af.getFileDetails()))
    				.map(af->{
    					fileService.createFile(af, saveFolder);
    					return af.getAtchFileNo();
    				}).orElse(null);
            question.setAtchFileNo(questionAtchFileNo);
            
            if(testQuestionMapper.insertTestQuestion(question) > 0 ) {
    			result = ServiceResult.OK;
    		} else {
    			result = ServiceResult.FAIL;
    		}
            resultMap.put(String.format("testQuestion_%d", questionIndex), result);
            
            
            // 지문 등록
            String queType = null;
            boolean first = true;
    		int itemIndex = 0;
            for (TestItemVO item : question.getItemList()) {
            	itemIndex++;
            	log.info("★★★★★★★★★★★★★★★★★★★★★★★★★");
            	log.info(item.toString());
            	log.info("★★★★★★★★★★★★★★★★★★★★★★★★★");
            	if(first == true) {
            		queType = item.getQueType();
            		first = false;
            	}
            	log.info("queType : " + queType);
                item.setQueNo(question.getQueNo());
                item.setQueType(queType);
                
                Integer itemAtchFileNo = Optional.ofNullable(item.getFile())
        				.filter(af->! CollectionUtils.isEmpty(af.getFileDetails()))
        				.map(af->{
        					fileService.createFile(af, saveFolder);
        					return af.getAtchFileNo();
        				}).orElse(null);
                item.setAtchFileNo(itemAtchFileNo);
                
                if(!"TE01".equals(testCd)) {
	            	// 문제 점수를 정답 선택지에 설정
	            	if ("Y".equals(item.getItemYn())) {
	            		item.setItemScore(question.getQueScore());
	            	} else {
	            		item.setItemScore(0);
	            	}                	
                } else {
                	item.setQueType("QT01");
                	log.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
                	log.info(String.valueOf(item.getItemScore()));
                	log.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
                }
                
                // 지문 등록
                if( testItemMapper.insertTestItem(item) > 0 ) {
        			result = ServiceResult.OK;
        		} else {
        			result = ServiceResult.FAIL;
        		}
                resultMap.put(String.format("testItem_%d", itemIndex), result);
                
                

            	log.info("★★★★★★★★★★★★★★★★★★★★★★★★★");
            	log.info(item.getQueType());
            	log.info("★★★★★★★★★★★★★★★★★★★★★★★★★");
                // 주관식 처리: queType == "N"인 경우 키워드 저장
                if ("QT02".equals(Optional.ofNullable(item.getQueType()).orElse("N")) && item.getKeywordList() != null) {
                	int keywordIndex = 0;
                    for (Test_KeywordVO keyword : item.getKeywordList()) {
                    	keywordIndex++;
                    	keyword.setItemNo(item.getItemNo());
                    	log.info("★★★★★★★★★★★★★★★★★★★★★★★★★");
                    	log.info(keyword.toString());
                    	log.info("★★★★★★★★★★★★★★★★★★★★★★★★★");
                        if(testKeywordMapper.insertTestKeyword(keyword) > 0 ) {
                			result = ServiceResult.OK;
                		} else {
                			result = ServiceResult.FAIL;
                		}
                        resultMap.put(String.format("testKeyword_%d", keywordIndex), result);
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
	public List<TestVO> readTestList(PaginationInfo paging, String testCd, String userId) {
		if(paging!=null) {
			int totalRecord = testMapper.selectTotalTest(paging, testCd, userId);
			paging.setTotalRecord(totalRecord);
		}
		
		List<TestVO> testList = testMapper.selectTestList(paging, testCd, userId);
		
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
	@Transactional
	public ServiceResult modifyTest(TestVOWrapper testVOWrapper) {
		ServiceResult result = null;
		Map<String, ServiceResult> resultMap = new HashMap<>();
		log.info(testVOWrapper.toString());
		// 테스트 업데이트
		if(testMapper.updateTest(testVOWrapper.getTest()) > 0 ) {
			result = ServiceResult.OK;
		} else {
			result = ServiceResult.FAIL;
		}
		resultMap.put("updateTest", result);
		
		// 기존 문제 데이터 조회
	    List<Test_QuestnVO> existingQuestionList = testQuestionMapper.selectTestQuestionList(testVOWrapper.getTest().getTestNo());
		
	    // 입력된 데이터중 0번 인덱스 null 문제 제거
	    List<Test_QuestnVO> newQuestionList = testVOWrapper.getTestQuestnList().stream()
	            .filter(question -> question.getQueCont() != null && !question.getQueCont().trim().isEmpty()) // 유효한 문제만 포함
	            .collect(Collectors.toList());
	    String testCd = testVOWrapper.getTest().getTestCd();
	    // 문제 업데이트
	    int questionIndex = 0;
	    for (Test_QuestnVO question : newQuestionList) {
	    	questionIndex++;
	    	if (question.getQueNo() == null) {
	            // 새로 추가된 문제
	            question.setTestNo(testVOWrapper.getTest().getTestNo());
	            
	            Integer questionAtchFileNo = Optional.ofNullable(question.getFile())
	    				.filter(af->! CollectionUtils.isEmpty(af.getFileDetails()))
	    				.map(af->{
	    					fileService.createFile(af, saveFolder);
	    					return af.getAtchFileNo();
	    				}).orElse(null);
	            question.setAtchFileNo(questionAtchFileNo);
	            if("TE01".equals(testCd)) {
	            	question.setQueScore(0);
	            }
	            if(testQuestionMapper.insertTestQuestion(question) > 0 ) {
	    			result = ServiceResult.OK;
	    		} else {
	    			result = ServiceResult.FAIL;
	    		}
	    		resultMap.put(String.format("updateQuestion_%d", questionIndex), result);
	    		
	    		// 지문 등록
	            String queType = null;
	            boolean first = true;
	    		int itemIndex = 0;
	            for (TestItemVO item : question.getItemList()) {
	            	itemIndex++;
	            	log.info("★★★★★★★★★★★★★★★★★★★★★★★★★");
	            	log.info(item.toString());
	            	log.info("★★★★★★★★★★★★★★★★★★★★★★★★★");
	            	if(first == true) {
	            		queType = item.getQueType();
	            		first = false;
	            	}
	            	if(!"TE01".equals(testCd)) {
	            		queType = "QT01";
	            	}
	            	log.info("queType : " + queType);
	                item.setQueNo(question.getQueNo());
	                item.setQueType(queType);
	                
	                Integer itemAtchFileNo = Optional.ofNullable(item.getFile())
	        				.filter(af->! CollectionUtils.isEmpty(af.getFileDetails()))
	        				.map(af->{
	        					fileService.createFile(af, saveFolder);
	        					return af.getAtchFileNo();
	        				}).orElse(null);
	                item.setAtchFileNo(itemAtchFileNo);
	                
	                if(!"TE01".equals(testCd)) {
		            	// 문제 점수를 정답 선택지에 설정
		            	if ("Y".equals(item.getItemYn())) {
		            		item.setItemScore(question.getQueScore());
		            	} else {
		            		item.setItemScore(0);
		            	}                	
	                }
	                
	                // 지문 등록
	                if( testItemMapper.insertTestItem(item) > 0 ) {
	        			result = ServiceResult.OK;
	        		} else {
	        			result = ServiceResult.FAIL;
	        		}
	                resultMap.put(String.format("testItem_%d", itemIndex), result);
	                
	                

	            	log.info("★★★★★★★★★★★★★★★★★★★★★★★★★");
	            	log.info(item.getQueType());
	            	log.info("★★★★★★★★★★★★★★★★★★★★★★★★★");
	                // 주관식 처리: queType == "N"인 경우 키워드 저장
	                if ("QT02".equals(Optional.ofNullable(item.getQueType()).orElse("N")) && item.getKeywordList() != null) {
	                	int keywordIndex = 0;
	                    for (Test_KeywordVO keyword : item.getKeywordList()) {
	                    	keywordIndex++;
	                    	keyword.setItemNo(item.getItemNo());
	                    	log.info("★★★★★★★★★★★★★★★★★★★★★★★★★");
	                    	log.info(keyword.toString());
	                    	log.info("★★★★★★★★★★★★★★★★★★★★★★★★★");
	                        if(testKeywordMapper.insertTestKeyword(keyword) > 0 ) {
	                			result = ServiceResult.OK;
	                		} else {
	                			result = ServiceResult.FAIL;
	                		}
	                        resultMap.put(String.format("testKeyword_%d", keywordIndex), result);
	                    }
	                }
	            }
	        } else {
	        	question.setTestNo(testVOWrapper.getTest().getTestNo());
	        	
	        	Test_QuestnVO saved = testQuestionMapper.selectTestQuestion(question.getQueNo());

	            Integer atchFileNo = Optional.ofNullable(question.getFile())
						 .filter(af -> af.getFileDetails() != null)
						 .map(af ->mergeSavedDetailsAndNewDetails(saved.getFile(), af, saveFolder))
						 .orElse(null);
	            
	            question.setAtchFileNo(atchFileNo);
	            if("TE01".equals(testCd)) {
	            	question.setQueScore(0);
	            }
	            // 기존 문제 업데이트
	            if(testQuestionMapper.updateTestQuestion(question) > 0 ) {
	    			result = ServiceResult.OK;
	    		} else {
	    			result = ServiceResult.FAIL;
	    		}
	            resultMap.put(String.format("updateQuestion_%d", questionIndex), result);
	            

	            // 기존 지문 데이터 조회
	            List<TestItemVO> existingItemList = testItemMapper.selectTestItemList(question.getQueNo());

	            // 지문 업데이트
	            String queType = null;
	            boolean first = true;
	            int itemIndex = 0;
	            for (TestItemVO item : question.getItemList()) {
	            	itemIndex++;
	                if (item.getItemNo() == null) {
	                	// 새로 추가된 지문
	                	if(first == true) {
	                		queType = item.getQueType();
	                		first = false;	                			
	                	}
	                	if("TE01".equals(testCd)) {
	                		queType = "QT01";
	                	}
	                	item.setQueNo(question.getQueNo());
	                	item.setQueType(queType);
	                    
	                	Integer itemAtchFileNo = Optional.ofNullable(item.getFile())
	            				.filter(af->! CollectionUtils.isEmpty(af.getFileDetails()))
	            				.map(af->{
	            					fileService.createFile(af, saveFolder);
	            					return af.getAtchFileNo();
	            				}).orElse(null);
	                    item.setAtchFileNo(itemAtchFileNo);
	                	
	                    // 문제 점수를 정답 선택지에 설정
	                    if(!"TE01".equals(testCd)) {
		                    if ("Y".equals(item.getItemYn())) {
		                    	item.setItemScore(question.getQueScore());
		                    } else {
		                    	item.setItemScore(0);
		                    }
	                    }
	                    if(testItemMapper.insertTestItem(item) > 0 ) {
	    	    			result = ServiceResult.OK;
	    	    		} else {
	    	    			result = ServiceResult.FAIL;
	    	    		}
	    	    		resultMap.put(String.format("updateItem_%d", itemIndex), result);
	                    
	                } else {
	                    // 기존 지문 업데이트
	                	if(first == true) {
	                		queType = item.getQueType();
	                		first = false;
	                	}
	                	if("TE01".equals(testCd)) {
	                		queType = "QT01";
	                	}
	                	item.setQueType(queType);
	                	
	                	TestItemVO savedItem = testItemMapper.selectTestItem(item.getItemNo());
	    	            
	    	            Integer itemAtchFileNo = Optional.ofNullable(item.getFile())
	    						 .filter(af -> af.getFileDetails() != null)
	    						 .map(af ->mergeSavedDetailsAndNewDetails(savedItem.getFile(), af, saveFolder))
	    						 .orElse(null);

	    	            item.setAtchFileNo(itemAtchFileNo);
	    	            
	    	            // 문제 점수를 정답 선택지에 설정
	    	            if(!"TE01".equals(testCd)) {
	    	            	if ("Y".equals(item.getItemYn())) {
	    	            		item.setItemScore(question.getQueScore());
	    	            	} else {
	    	            		item.setItemScore(0);
	    	            	}
	    	            }
	                    
	                    if(testItemMapper.updateTestItem(item) > 0 ) {
	    	    			result = ServiceResult.OK;
	    	    		} else {
	    	    			result = ServiceResult.FAIL;
	    	    		}
	                    resultMap.put(String.format("updateItem_%d", itemIndex), result);

	                    // 기존 키워드 데이터 조회
	                    List<Test_KeywordVO> existingKeywordList = testKeywordMapper.selectTestKeywordList(item.getItemNo());

	                    // 키워드 업데이트
	                    if ("QT02".equals(Optional.ofNullable(item.getQueType()).orElse("N")) && item.getKeywordList() != null) {
		                    int keywordIndex = 0;
		                    for (Test_KeywordVO keyword : item.getKeywordList()) {
		                    	keywordIndex++;
		                        if (keyword.getKeywdNo() == null) {
		                            // 새로 추가된 키워드
		                            keyword.setItemNo(item.getItemNo());
		                            
		                            if(testKeywordMapper.insertTestKeyword(keyword) > 0 ) {
		    	    	    			result = ServiceResult.OK;
		    	    	    		} else {
		    	    	    			result = ServiceResult.FAIL;
		    	    	    		}
		                            resultMap.put(String.format("updateKeyword_%d", keywordIndex), result);
		                        } else {
		                            // 기존 키워드 업데이트
		                            
		                            if(testKeywordMapper.updateTestKeyword(keyword) > 0 ) {
		    	    	    			result = ServiceResult.OK;
		    	    	    		} else {
		    	    	    			result = ServiceResult.FAIL;
		    	    	    		}
		                            resultMap.put(String.format("updateKeyword_%d", keywordIndex), result);
		                        }
		                    }
	                    }

	                    int[] deleteKeywordIndex = {0};
	                    // 삭제된 키워드 처리
	                    List<Integer> updatedKeywordNoList = Optional.ofNullable(item.getKeywordList())
	                    	    .orElse(Collections.emptyList()) // NullPointerException 방지
	                    	    .stream()
	                    	    .map(Test_KeywordVO::getKeywdNo)
	                    	    .filter(Objects::nonNull) // keywdNo가 null인 경우 제외
	                    	    .collect(Collectors.toList());
	                    existingKeywordList.stream()
	                            .filter(keyword -> !updatedKeywordNoList.contains(keyword.getKeywdNo()))
	                            .forEach(keyword -> {
	                            	if(testKeywordMapper.deleteTestKeyword(keyword.getKeywdNo()) > 0 ) {
		    	    	    			resultMap.put(String.format("deleteTestKeyword_%d", deleteKeywordIndex[0]), ServiceResult.OK);
		    	    	    		} else {
		    	    	    			resultMap.put(String.format("deleteTestKeyword_%d", deleteKeywordIndex[0]), ServiceResult.OK);
		    	    	    		}
	                            	deleteKeywordIndex[0]++;
	                            });
	                }
	            }

	            int[] deleteItemIndex = {0};
	            // 삭제된 지문 처리
	            List<Integer> updatedItemNoList = question.getItemList().stream()
	                    .map(TestItemVO::getItemNo)
	                    .collect(Collectors.toList());
	            existingItemList.stream()
	                    .filter(item -> !updatedItemNoList.contains(item.getItemNo()))
	                    .forEach(item -> {
	                    	if(testItemMapper.deleteTestItem(item.getItemNo()) > 0 ) {
    	    	    			resultMap.put(String.format("deleteItem_%d", deleteItemIndex[0]), ServiceResult.OK);
    	    	    		} else {
    	    	    			resultMap.put(String.format("deleteItem_%d", deleteItemIndex[0]), ServiceResult.OK);
    	    	    		}
	                    	deleteItemIndex[0]++;
	                    });
	        }
	    }

	    // 삭제된 문제 처리
	    int[] deleteQuestionIndex = {0};
	    List<Integer> updatedQuestionNoList = testVOWrapper.getTestQuestnList().stream()
	            .map(Test_QuestnVO::getQueNo)
	            .collect(Collectors.toList());
	    existingQuestionList.stream()
	            .filter(question -> !updatedQuestionNoList.contains(question.getQueNo()))
	            .forEach(question -> {
	            	
	            	if(testQuestionMapper.deleteTestQuestion(question.getQueNo()) > 0 ) {
    	    			resultMap.put(String.format("deleteQuestion_%d", deleteQuestionIndex[0]), ServiceResult.OK);
    	    		} else {
    	    			resultMap.put(String.format("deleteQuestion_%d", deleteQuestionIndex[0]), ServiceResult.OK);
    	    		}
	            	deleteQuestionIndex[0]++;
	            });

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

	@Override
	public int readTotalTest(PaginationInfo paging, String testCd, String userId) {
		return testMapper.selectTotalTest(paging, testCd, userId);
	}
	
	private Integer mergeSavedDetailsAndNewDetails(FileVO savedAtchFile, FileVO newAtchFile, File saveFolder) {
		FileVO mergeAtchFile = new FileVO();
		List<File_DetailVO> fileDetails = Stream.concat(
				Optional.ofNullable(savedAtchFile)
				.filter(saf->! CollectionUtils.isEmpty(saf.getFileDetails()))
					.map(saf->saf.getFileDetails().stream())
					.orElse(Stream.empty())
			, Optional.ofNullable(newAtchFile)
					.filter(naf->! CollectionUtils.isEmpty(naf.getFileDetails()))
					.map(naf->naf.getFileDetails().stream())
					.orElse(Stream.empty())
		).collect(Collectors.toList());	
		
			mergeAtchFile.setFileDetails(fileDetails);
		
		if( ! mergeAtchFile.getFileDetails().isEmpty() ) {
			fileService.createFile(mergeAtchFile, saveFolder);
		}
		
		if (savedAtchFile != null && savedAtchFile.getFileDetails() != null) {
			// 기존 첨부파일 그룹은 비활성화
			fileService.disableFile(savedAtchFile.getAtchFileNo());
		}
		
		return mergeAtchFile.getAtchFileNo();
	}

	@Override
	public void removeFile(int atchFileNo, int fileSn) {
		fileService.removeFileDetail(atchFileNo, fileSn, saveFolder);
	}

	@Override
	public File_DetailVO download(int atchFileNo, int fileSn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceResult removeCompTestList(List<Integer> testNo) {
		ServiceResult result = null;
		if(testMapper.deleteCompTestList(testNo) > 0 ) {
			result = ServiceResult.OK;
		} else {
			result = ServiceResult.FAIL;
		}
		return result;
	}

}
