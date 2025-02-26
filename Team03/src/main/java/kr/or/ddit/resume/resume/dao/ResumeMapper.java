package kr.or.ddit.resume.resume.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.resume.resume.ResumeDTO;
import kr.or.ddit.vo.ActivityVO;
import kr.or.ddit.vo.AwardVO;
import kr.or.ddit.vo.CareerVO;
import kr.or.ddit.vo.CertVO;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.CompVO;
import kr.or.ddit.vo.EduVO;
import kr.or.ddit.vo.ExperienceVO;
import kr.or.ddit.vo.HighSchoolVO;
import kr.or.ddit.vo.Lang_TestVO;
import kr.or.ddit.vo.LanguageVO;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PortfolioVO;
import kr.or.ddit.vo.PrefVO;
import kr.or.ddit.vo.QualificationVO;
import kr.or.ddit.vo.UniVO;
import kr.or.ddit.vo.Work_CityVO;
import kr.or.ddit.vo.Work_CondVO;

@Mapper
public interface ResumeMapper {
	
	/**
	 * 학교 재학 상태 리스트
	 * @return
	 */
	public List<CodeVO> selectGraduationList();
	/**
	 * 학교 재학 상태 리스트
	 * @return
	 */
	public List<CodeVO> selectHighMajorList();
	
	/**
	 * 학교 입학 상태 리스트
	 * @return
	 */
	public List<CodeVO> selectAdmissionList();
	
	/**
	 * 대학 년제 종류 리스트
	 * @return
	 */
	public List<CodeVO> selectUnivTypeList();
	
	/**
	 * 대학 전공 종류 리스트
	 * @return
	 */
	public List<CodeVO> selectUnivMajorList();
	
	/**
	 * 대학 학사 리스트
	 * @return
	 */
	public List<CodeVO> selectUnivDegreeList();
	
	/**
	 * 업종 리스트
	 * @return
	 */
	public List<CodeVO> selectIndustryList();
	

	/**
	 * 상세 업종 리스트
	 * @param codeParent
	 * @return
	 */
	public List<CodeVO> selectIndustryDetailList();
	
	/**
	 * 기업 규모 사이즈
	 * @param codeParent
	 * @return
	 */
	public List<CodeVO> selectCompanySizeList();
	
	/**
	 * 기업 타입
	 * @param codeParent
	 * @return
	 */
	public List<CodeVO> selectCompanyTypeList();
	
	/**
	 * 기업 상장 여부
	 * @param codeParent
	 * @return
	 */
	public List<CodeVO> selectCompanyListingList();
	
	/**
	 * 시도 리스트
	 * @param codeParent
	 * @return
	 */
	public List<CodeVO> selectCityList();
	
	/**
	 * 군구 리스트
	 * @param codeParent
	 * @return
	 */
	public List<CodeVO> selectCityDetailList();
	
	/**
	 * 근무형태 리스트
	 * @return
	 */
	public List<CodeVO> selectWorkTypeList();
	
	/**
	 * 퇴사사유 리스트
	 * @return
	 */
	
	public List<CodeVO> selectResignReasonList();
	
	/**
	 * 자격증 취득 단계 리스트
	 * @return
	 */
	public List<CodeVO> selectCretList();
	
	/**
	 * 컴활 리스트
	 * @return
	 */
	public List<CodeVO> selectComList();
	
	/**
	 * 컴활 세부 리스트
	 * @return
	 */
	public List<CodeVO> selectComDetailList();
	
	/**
	 * 컴활 능력 리스트
	 * @return
	 */
	public List<CodeVO> selectComLVList();
	
	/**
	 * 외국어 리스트
	 * @return
	 */
	public List<CodeVO> selectLangList();
	
	/**
	 * 회화수준 리스트
	 * @return
	 */
	public List<CodeVO> selectSpeakList();
	
	/**
	 * 독해수준 리스트
	 * @return
	 */
	public List<CodeVO> selectReadList();
	
	/**
	 * 작문수준 리스트
	 * @return
	 */
	public List<CodeVO> selectWriteList();
	
	/**
	 * 외국어 시험 리스트
	 * @return
	 */
	public List<CodeVO> selectLangTestList();
	
	/**
	 * 국가명 리스트
	 * @return
	 */
	public List<CodeVO> selectCountryList();
	
	/**
	 * 연봉 범위 리스트
	 * @return
	 */
	public List<CodeVO> selectSalaryList();
	
	/**
	 * URL
	 * @return
	 */
	public List<CodeVO> selectURLList();
	
	/**
	 * 자격증
	 * @return
	 */
	public List<CodeVO> selectCertList();
	
	//--------------------------------------------
	
	/**
	 * 이력서 등록
	 * @param resume
	 * @return 0 이상이면 성공!
	 */
	public int insertAndUpdateResume(String memId,ResumeDTO resume);
	
	public int updateMemberImage(MemberVO member);
	
	
}
