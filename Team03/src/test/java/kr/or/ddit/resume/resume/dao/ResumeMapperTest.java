package kr.or.ddit.resume.resume.dao;

import static org.junit.jupiter.api.Assertions.*;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.annotation.RootContextWebConfig;
import kr.or.ddit.vo.ActivityVO;
import kr.or.ddit.vo.AwardVO;
import kr.or.ddit.vo.CareerVO;
import kr.or.ddit.vo.CertVO;
import kr.or.ddit.vo.CompVO;
import kr.or.ddit.vo.EduVO;
import kr.or.ddit.vo.ExperienceVO;
import kr.or.ddit.vo.HighSchoolVO;
import kr.or.ddit.vo.Lang_TestVO;
import kr.or.ddit.vo.LanguageVO;
import kr.or.ddit.vo.PortfolioVO;
import kr.or.ddit.vo.PrefVO;
import kr.or.ddit.vo.QualificationVO;
import kr.or.ddit.vo.UniVO;
import kr.or.ddit.vo.Work_CondVO;
import lombok.extern.slf4j.Slf4j;

@RootContextWebConfig
@Slf4j
class ResumeMapperTest {
	
	@Inject
	ResumeMapper mapper;
	
	@Test
	void testInsertResume() {
		fail("Not yet implemented");
	}

	@Test
	void testInsertActivity() {
		ActivityVO activity = new ActivityVO();
		activity.setActivityNo(1);
		activity.setActivityOrganization("대덕봉사");
		activity.setActivityDesc("활동내용");
		activity.setActivitySdt("20221010");
		activity.setActivityEdt("20241219");
		activity.setMemId("emp001");
//		log.info("{}",mapper.insertActivity(activity));
	}

	@Test
	void testInsertAward() {
		AwardVO award = new AwardVO();
		award.setAwardNo(1);
		award.setAwardTitle("수상내역");
		award.setAwardIssuer("수여기관");
		award.setAwardDate("20241219");
		award.setMemId("emp001");
//		log.info("{}",mapper.insertAward(award));
	}

	@Test
	void testInsertCareer() {
		CareerVO career = new CareerVO();
		career.setCareerNo(1);
		career.setCareerCompanyNm("회사명");
		career.setCareerIndustryType("업종");
		career.setCareerSubIndustry("상세업종");
		career.setCareerCompanySize("기업규모");
		career.setCareerCompanyType("기업형태");
		career.setCareerListed("상장여부");
		career.setCareerCity("시,도");
		career.setCareerDistrict("구,군");
		career.setCareerStartDate("20241218");
		career.setCareerEndDate("20241219");
		career.setCareerJobTitle("담당업무");
		career.setCareerJobDesc("업무내용");
		career.setCareerPosition("직급");
		career.setCareerDepartment("근무부서");
		career.setCareerCompanyType("근무형태");
		career.setCareerSalary(3000);
		career.setCareerReason("퇴사사유");
		career.setMemId("emp001");
//		log.info("{}",mapper.insertCareer(career));
	}

	@Test
	@Transactional
	void testInsertComp() {
		CompVO comp = new CompVO();
		comp.setCompSkillNo(1);
		comp.setCompSkillField("분야선택");
		comp.setCompSkillDetail("상세분야");
		comp.setCompSkillLevel("활용능력수준");
		comp.setMemId("emp001");
//		log.info("{}",mapper.insertComp(comp));
	}

	@Test
	void testInsertCert() {
		CertVO cert = new CertVO();
		cert.setCertNo(1);
		cert.setCertNm("자격증명");
		cert.setCertIssuer("자격증발행처");
		cert.setCertDate("20241219");
		cert.setCertPassCd("자격증합격구분");
		cert.setMemId("emp001");
//		log.info("{}",mapper.insertCert(cert));
	}

	@Test
	@Transactional
	void testInsertEdu() {
		EduVO edu = new EduVO();
		edu.setEduNo(1);
		edu.setEduTitle("과정명");
		edu.setEduInstitution("교육기관");
		edu.setEduSdt("20241218");
		edu.setEduEdt("20241219");
		edu.setEduDesc("수료내용");
		edu.setMemId("emp001");
//		log.info("{}",mapper.insertEdu(edu));
	}

	@Test
	@Transactional
	void testInsertExperience() {
		ExperienceVO experience = new ExperienceVO();
		experience.setExpNo(1);
		experience.setExpCountry("국가명");
		experience.setExpSdt("20241218");
		experience.setExpEdt("20241219");
		experience.setExpDesc("목적및내용");
		experience.setMemId("emp001");
//		log.info("{}",mapper.insertExperience(experience));
	}

	@Test
	@Transactional
	void testInsertHighschool() {
		HighSchoolVO highSchool = new HighSchoolVO();
		highSchool.setHighschoolNo(1);
		highSchool.setHighschoolNm("고등학교명");
		highSchool.setHighschoolLocation("고등학교소재지");
		highSchool.setHighschoolPeriod("20241219");
		highSchool.setHighschoolStatus("고등학교재학상태");
		highSchool.setHighschoolMajor("고등학교전공");
		highSchool.setMemId("emp001");
//		log.info("{}",mapper.insertHighschool(highSchool));
	}

	@Test
	@Transactional
	void testInsertLangTest() {
		Lang_TestVO langTest = new Lang_TestVO();
		langTest.setLangTestNo(1);
		langTest.setLangTestName("어학시험명");
		langTest.setLangTestLevel("점수및등급");
		langTest.setLangTestDate("20241219");
		langTest.setMemId("emp001");
//		log.info("{}",mapper.insertLangTest(langTest));
	}

	@Test
	@Transactional
	void testInsertLanguage() {
		LanguageVO language = new LanguageVO();
		language.setLangNo(1);
		language.setLangNm("외국어명");
		language.setLangSpeakingLevel("회화수준");
		language.setLangReadingLevel("독해수준");
		language.setLangWritingLevel("작문수준");
		language.setMemId("emp001");
//		log.info("{}",mapper.insertLanguage(language));
	}

	@Test
	@Transactional
	void testInsertPortfolio() {
		PortfolioVO portfolio = new PortfolioVO();
		portfolio.setPortNo(1);
		portfolio.setPortNm("포트폴리오구분");
		portfolio.setPortUrl("포트폴리오주소");
		portfolio.setMemId("emp001");
//		log.info("{}",mapper.insertPortfolio(portfolio));
	}

	@Test
	@Transactional
	void testInsertPref() {
		PrefVO pref = new PrefVO();
		pref.setPrefNo(1);
		pref.setPrefMilitary("병역사항");
		pref.setPrefPatriot("N");
		pref.setPrefDisability("N");
		pref.setPrefEmploySupport("N");
		pref.setPrefHobbies("취미,관심");
		pref.setPrefSkills("특기");
		pref.setMemId("emp001");
//		log.info("{}",mapper.insertPref(pref));
		
	}

	@Test
	@Transactional
	void testInsertQualfication() {
		QualificationVO qualification = new QualificationVO();
		qualification.setQualificationNo(1);
		qualification.setQualificationDt("20241219");
		qualification.setMemId("emp001");
//		log.info("{}",mapper.insertQualfication(qualification));
	}

	@Test
	@Transactional
	void testInsertUni() {
		UniVO uni = new UniVO();
		uni.setUniNo(1);				
		uni.setUniNm("대학교명");	
		uni.setUniType("대학종류");    		
		uni.setUniSdt("20121212");    			
		uni.setUniSStatus("입학");    		
		uni.setUniEdt("20241212");    			
		uni.setUniEStatus("졸업");    		
		uni.setUniMajorCategory("전공계열");
		uni.setUniMajorNm("전공명");
		uni.setUniMajorDegree("석박사");    		
		uni.setUniGpa(4.5);
		uni.setUniProjectNm("수업 및 프로젝트명");    
		uni.setUniProjectDesc("수업내용");
		uni.setMemId("emp001");
//		log.info("{}",mapper.insertUni(uni));
	}

	@Test
	@Transactional
	void testInsertWorkCond() {
		Work_CondVO workCond = new Work_CondVO();
		workCond.setWorkCondNo(1);
		workCond.setWorkCondRemote("N");
		workCond.setWorkCondJobType("직종");
		workCond.setWorkCondType("근무형태");
		workCond.setWorkCondSalary("2500~3000");
		workCond.setWorkCondSalaryVisible("N");
		workCond.setMemId("emp001");
//		log.info("{}",mapper.insertWorkCond(workCond));
	}

}
