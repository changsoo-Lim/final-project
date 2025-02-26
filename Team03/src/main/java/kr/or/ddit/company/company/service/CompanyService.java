package kr.or.ddit.company.company.service;

import java.util.List;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.vo.Cmp_BftVO;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.CompanyVO;
import kr.or.ddit.vo.EmployVO;
import kr.or.ddit.vo.ProjectVO;
import kr.or.ddit.vo.ReviewVO;

public interface CompanyService {
	
	/**
	 * 기업 상세 조회
	 * @param compId (조회할 기업 아이디)
	 * @return CompanyVO
	 */
	public CompanyVO readCompany(String compId);
	
	/**
	 * 기업 탈퇴
	 * @param compId
	 * @return
	 */
	public ServiceResult removeCompany(String compId);
	
	/**
	 * 기업 소개 내용 수정
	 * @param compVO ( 수정할 기업 소개 )
	 * @return 성공 여부
	 */
	public ServiceResult modifyCompContent(CompanyVO compVO);
	
	/**
	 * 공통 코드 리스트
	 * @return
	 */
	public List<CodeVO> readCode(); 
	
	/**
	 * 기업 회원가입 휴대폰 중복 체크 
	 * @param compMobile
	 * @return CompanyVO 
	 */
	public String readCompMobile(String compMobile);
	
	/**
	 * 기업 담당자 전화번호, 이메일, 사무실 전화번호 업데이트
	 * @param company ( 업데이트 정보)
	 */
	public ServiceResult modifyInfo(CompanyVO company);
	
	/**
	 * 기업 로고 변경
	 * @param company
	 * @return
	 */
	public ServiceResult updateCompanyImage(CompanyVO company);
	
	/**
	 * 기업 공고 조회
	 * @param username
	 * @return
	 */
	public List<EmployVO> selectEmpList(String username);
	
	/**
	 * 기업 복리후생 조회
	 * @param username
	 * @return
	 */
	public List<Cmp_BftVO> selectBenefitList(String username);
	
	/**
	 * 기업 리뷰 조회
	 * @param username
	 * @return
	 */
	public List<ReviewVO> selectReviewList(String username);
	
	/**
	 * 기업 프로젝트 조회
	 * @param username
	 * @return
	 */
	public List<ProjectVO> selectProjectList(String username);

}
