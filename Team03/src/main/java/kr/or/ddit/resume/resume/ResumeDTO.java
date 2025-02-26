package kr.or.ddit.resume.resume;


import java.util.List;

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
import kr.or.ddit.vo.Work_CityVO;
import kr.or.ddit.vo.Work_CondVO;
import lombok.Data;

@Data
public class ResumeDTO {
	
	private List<QualificationVO> qualification;		// 검정고시

	private List<HighSchoolVO> highSchool;			// 고등학교
	
	private List<UniVO> uni;					// 대학교

	private List<CareerVO> career; 				// 경력사항
	
	private List<CertVO> cert; 					// 자격증
	
	private List<CompVO> comp; 					// 컴활
	
	private List<PrefVO> pref;					// 취업우대 및 특이사항
	
	private List<AwardVO> award; 				// 수상경력
	
	private List<EduVO> edu; 					// 교육수료사항
	
	private List<LanguageVO> language;			// 어학능력
	
	private List<Lang_TestVO> langTest;			// 어학시험
	
	private List<ExperienceVO> experience;		// 해외연수
	
	private List<ActivityVO> activity;			// 봉사활동
	
	private List<PortfolioVO> portfolio;		// 포트폴리오
	
	private List<Work_CondVO> workCond;				// 희망근무조건
	
	private List<Work_CityVO> workCity;			// 희망근무지역
}
