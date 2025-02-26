package kr.or.ddit.company.company.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.CompanyVO;
import kr.or.ddit.vo.EmployVO;

@Mapper
public interface CompanyMapper {
	
	/**
	 * 기업의 리스트 출력
	 * 
	 * @return companyVO 리스트
	 */
	public List<CompanyVO> selectCompanyList();

	/**
	 * 기업 정보 단건 조회
	 * @param CompId (기업 아이디)
	 * @return companyVO
	 */
	public CompanyVO selectCompany(String compId);

	/**
	 * 기업정보 업데이트
	 * @param compVO (업데이트할 기업 정보)
	 * @return 성공 실패 여부
	 */
	public int updateCompany(CompanyVO compVO);

	/**
	 * 기업 회원 탈퇴 기업 상태코드 업데이트 ( Y : 탈퇴 )
	 * @param compId
	 * @return
	 */
	public int deleteCompany(String compId);
	
	/**
	 * 전체기업 소개 조회 
	 * @return 기업VO 리스트
	 */
	public List<CompanyVO> selectCompContentList();
	
	/**
	 * 기업 소개 단일 조회
	 * @param compId (조회할 기업 아이디)
	 * @return 기업VO
	 */
	public CompanyVO selectCompContent(String compId);
	
	/**
	 * 기업 소개 작성
	 * @param compVO ( 생성할 기업 소개 내용)
	 * @return 성공 여부
	 */
	public int insertCompContent(CompanyVO compVO);
	
	/**
	 * 기업 소개 업데이트
	 * @param compVO (업데이트할 기업소개 내용)
	 * @return 성공 여부
	 */
	public int updateCompContent(CompanyVO compVO);
	
	/**
	 * 기업 소개 삭제 
	 * @param compId (삭제할 기업ID , 삭제여부 N 으로 업데이트)
	 * @return 성공 여부
	 */
	public int deleteCompContent(String compId);
	
	public List<CodeVO> selecteCode();
	
	/**
	 * 기업 회원가입 휴대폰 중복 체크 
	 * @param compMobile
	 * @return CompanyVO 
	 */
	public CompanyVO selectCompMobile(String compMobile);

	/**
	 * 기업 담당자 이메일, 전화번호, 사무실 전화번호 업데이트
	 * @param company (업데이트할 정보)
	 * @return 성공 여부
	 */
	public int updateInfo(CompanyVO company);
	
	/**
	 * 기업 로고 변경
	 * @param company
	 * @return
	 */
	public int updateCompanyImage(CompanyVO company);
	
	/**
	 * 추천 공고 생성에서 사용할 공고 리스트 출력
	 * @return
	 */
	public List<CompanyVO> selectRecommendList();
	
	/**
	 * 기업 공고 리스트
	 * @param compId
	 * @return
	 */
	public List<EmployVO> selectCompEmpList(String compId);

}
