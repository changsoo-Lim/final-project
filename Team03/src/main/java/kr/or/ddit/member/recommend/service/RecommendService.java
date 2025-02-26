package kr.or.ddit.member.recommend.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.company.company.dao.CompanyMapper;
import kr.or.ddit.member.member.dao.MemberMapper;
import kr.or.ddit.vo.CertVO;
import kr.or.ddit.vo.CompanyVO;
import kr.or.ddit.vo.EmployVO;
import kr.or.ddit.vo.FieldVO;
import kr.or.ddit.vo.FilterVO;
import kr.or.ddit.vo.LanguageVO;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.UniVO;
import kr.or.ddit.vo.Work_CityVO;
import kr.or.ddit.vo.Work_CondVO;

@Service
public class RecommendService {

	@Inject
	private MemberMapper memberMapper;
	@Inject
	private CompanyMapper companyMapper;

	private final RestTemplate restTemplate = new RestTemplate();

	public Map<String, Object> getRecommendations(String userPreference) {
//		String url = "http://172.20.10.3:5000/recommnd";
//		String url = "http://192.168.45.15:5000/recommend";
		//String url = "http://192.168.46.58:5000/recommend";
		String url = "http://127.0.0.1:5000/recommend";
		
		//String url = "http://192.168.146.17:5000/recommend";  //학원 
		Map<String, Object> requestBody = new HashMap<>();
		MemberVO member = memberMapper.selectMember(userPreference);
		Map<String, Object> dataMap = generateCandidateData(member);

		List<Map<String, Object>> targetData = generateHardCodedTargetData(companyMapper.selectRecommendList());

		ObjectMapper objectMapper = new ObjectMapper();
		String json = "{}";
		

		try {
			json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dataMap);
			System.out.println("Serialized JSON: " + json);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		requestBody.put("member", json);
		requestBody.put("target", targetData);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

		try {
			ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);

			if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
				Map<String, Object> responseBody = response.getBody();

				// 데이터 추출 및 반환
				return responseBody;
			} else {
				throw new RuntimeException("Unexpected Flask response: " + response.getStatusCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error during Flask request: " + e.getMessage());
		}
	}
	// 후보자 데이터 생성 메서드
	private Map<String, Object> generateCandidateData(MemberVO member) {
		Map<String, Object> dataMap = new HashMap<>();

		// 계약형태 및 급여조건
		List<Map<String, Object>> workConditions = new ArrayList<>();
		for (Work_CondVO workCond : member.getWorkCondList()) {
			Map<String, Object> workCondMap = new HashMap<>();
			workCondMap.put("work_cond_salary", workCond.getWorkCondSalary());

			// 지역 정보 추가
			List<Map<String, String>> cities = new ArrayList<>();
			for (Work_CityVO city : workCond.getWorkCity()) {
				Map<String, String> cityMap = new HashMap<>();
				cityMap.put("sido_cd", city.getSidoCd());
				cityMap.put("gugun_cd", city.getGugunCd());
				cities.add(cityMap);
			}
			workCondMap.put("city", cities);

			// 근무 형태 추가 (쉼표로 구분된 문자열 -> 리스트 변환)
			if (workCond.getWorkCondType() != null) {
				List<String> workCondTypeList = Arrays.stream(workCond.getWorkCondType().split(",")).map(String::trim) // 앞뒤
						.collect(Collectors.toList());
				workCondMap.put("work_cond_type", workCondTypeList);
			} else {
				workCondMap.put("work_cond_type", new ArrayList<>()); // 기본값: 빈 리스트
			}

			workConditions.add(workCondMap);
		}
		dataMap.put("work_cond", workConditions);

		// 대학 정보 추가
		List<Map<String, String>> universities = new ArrayList<>();
		for (UniVO uni : member.getUniList()) {
			Map<String, String> uniMap = new HashMap<>();
			uniMap.put("uni_type", uni.getUniType());
			uniMap.put("uni_major_category", uni.getUniMajorCategory());
			universities.add(uniMap);
		}
		dataMap.put("uni", universities);

		// 언어 정보 추가
		dataMap.put("lang_nm",
				member.getLanguageList().stream().map(LanguageVO::getLangNm).collect(Collectors.toList()));

		// 자격증 정보 추가
		dataMap.put("cert_nm", member.getCertrList().stream().map(CertVO::getCertNm).collect(Collectors.toList()));

		return dataMap;
	}

	// 하드코딩된 타겟 데이터 생성 메서드
	private List<Map<String, Object>> generateHardCodedTargetData(List<CompanyVO> compList) {
		List<Map<String, Object>> targetData = new ArrayList<>();

		ObjectMapper objectMapper = new ObjectMapper();

		for (CompanyVO company : compList) {
	        if (company == null) {
	            System.out.println("Warning: company is null in compList");
	            continue;
	        }

	        if (company.getEmployList() == null) {
	            System.out.println("Warning: company.getEmployList() is null for company: " + company.getCompId());
	            continue;
	        }

	        for (EmployVO employ : company.getEmployList()) {
	            if (employ == null) {
	                System.out.println("Warning: employ is null in company: " + company.getCompId());
	                continue;
	            }

	            if (employ.getFieldList() == null) {
	                System.out.println("Warning: employ.getFieldList() is null for employ: " + employ.getEmployNo());
	                continue;
	            }

	            for (FieldVO field : employ.getFieldList()) {
	                if (field == null) {
	                    System.out.println("Warning: field is null in employ: " + employ.getEmployNo());
	                    continue;
	                }

	                Map<String, Object> target = new HashMap<>();

	                // 근무 형태 처리
	                String employType = employ.getEmployType();
	                if (employType != null) {
	                    String[] workCondType = employType.split(",");
	                    target.put("workCondType",
	                            Arrays.stream(workCondType).map(String::trim).collect(Collectors.toList()));
	                } else {
	                    target.put("workCondType", new ArrayList<String>()); // 기본값: 빈 리스트
	                }

	                // 급여 조건
	                String employSalary = employ.getEmploySalary();
	                if (employSalary != null) {
	                    target.put("workCondSalary", employSalary);
	                } else {
	                    target.put("workCondSalary", "default"); // 기본값 설정
	                }

	                // 지역 정보 처리
	                List<Map<String, String>> workCity = new ArrayList<>();
	                String sidoCd = field.getFiledRegion(); // 오타 수정
	                String gugunCd = field.getFiledRegionGungu(); // 오타 수정
	                if (sidoCd != null && gugunCd != null) {
	                    Map<String, String> cityMap = new HashMap<>();
	                    cityMap.put("sido_cd", sidoCd);
	                    cityMap.put("gugun_cd", gugunCd);
	                    workCity.add(cityMap);
	                } else {
	                }
	                target.put("workCity", workCity);

	                // 타겟 ID
	                Integer fieldNo = field.getFiledNo(); // 오타 수정
	                if (fieldNo != null) {
	                    target.put("id", fieldNo);
	                } else {
	                    target.put("id", "default-id");
	                }

	                // 자격요건 처리
	                List<String> certifications = new ArrayList<>();
	                String uniType = "";
	                String uniMajorNm = "";
	                List<String> languages = new ArrayList<>();

	                if (field.getFilterList() != null) {
	                    for (FilterVO filter : field.getFilterList()) {
	                        if (filter == null) {
	                            continue;
	                        }
	                        if (filter.getFilterTitleCd() == null || filter.getFilterTitleCd() == null) {
	                            continue;
	                        }

	                        switch (filter.getFilterTitleCd()) {
	                            case "FILTER01": // 자격 및 면허사항
	                                certifications.add(filter.getFilterContCd());
	                                break;
	                            case "FILTER02": // 학력사항
	                                uniType = filter.getFilterContCd();
	                                break;
	                            case "FILTER03": // 전공(계열)
	                                uniMajorNm = filter.getFilterContCd();
	                                break;
	                            case "FILTER04": // 외국어 능력
	                                languages.add(filter.getFilterContCd());
	                                break;
	                            default:
	                                break;
	                        }
	                    }
	                } else {
	                }

	                // 자격증 필드 추가 (리스트 형태로)
	                target.put("certification", certifications.isEmpty() ? null : certifications);

	                // 학력 유형 추가
	                target.put("uniType", uniType.isEmpty() ? null : uniType);

	                // 전공 추가
	                target.put("uniMajorNm", uniMajorNm.isEmpty() ? null : uniMajorNm);

	                // 언어 추가 (리스트 형태로)
	                target.put("language", languages.isEmpty() ? null : languages);

	                // 추가 필드가 필요한 경우 여기서 추가

	                targetData.add(target);
	            }
	        }
	    }

	    // 생성된 targetData 로깅
	    try {
	        String targetDataJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(targetData);
	    } catch (JsonProcessingException e) {
	        e.printStackTrace();
	    }

		return targetData;
	}

}
