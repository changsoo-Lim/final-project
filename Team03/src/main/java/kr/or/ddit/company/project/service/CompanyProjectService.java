package kr.or.ddit.company.project.service;

import java.util.List;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.CompanyVO;
import kr.or.ddit.vo.Free_OfferVO;
import kr.or.ddit.vo.FreelancerVO;
import kr.or.ddit.vo.ProjectVO;

public interface CompanyProjectService {
	
	/**
	 * 공통 코드 
	 * @return
	 */
	public List<CodeVO> getCode();
	
	/**
	 * 프로젝트 생성
	 * @return
	 */
	public ServiceResult insertProject(ProjectVO project);
	
	/**
	 * 기업의 프로젝트 리스트 검색
	 * @param compId
	 * @return
	 */
	public List<ProjectVO> selectProjectList(String compId);
	
	/**
	 * 프로젝트 단일검색
	 * @param projectIf
	 * @return
	 */
	public ProjectVO selectProjectOne(String projectId);
	
	/**
	 * 프로젝트 수정
	 * @param project
	 * @return
	 */
	public ServiceResult updateProject(ProjectVO project);
	
	/**
	 * 프로젝트 삭제
	 * @param projectId
	 * @return
	 */
	public ServiceResult deleteProject(String projectId);
	
	
	public List<ProjectVO> selectProjectListOneComp(PaginationInfo<FreelancerVO> paging, String compId);
	
	/**
	 * 디테일 코드 리스트 
	 * @return
	 */
	public List<CodeVO> detailCode();
	
	/**
	 * 디테일용 셀렉트
	 * @param projectId
	 * @return
	 */
	public ProjectVO selectProjectDetail(String projectId);
	
	/**
	 * 회원 프로젝트 기업 조회
	 * @param compId
	 * @return
	 */
	public CompanyVO selectCompay(String compId);

	/**
	 * 유저 제안 정보 조회
	 * @param username
	 * @param projectId
	 * @return
	 */
	public Free_OfferVO selectFreeOffe(String username, String projectId);
	
	/**
	 * 기업 프로젝트 디테일 조회
	 * @param projectId
	 * @return
	 */
	public ProjectVO selectCompnyProjectDetail(String projectId);

	/**
	 * 프로젝트 제안 취소
	 * @param projectNo
	 * @param idList
	 */
	public ServiceResult deleteMember(String projectNo, List<String> idList);
	
	/**
	 * 컨트롤러 실행시 Postconstruct로 상태 업데이트
	 */
	public void constructUpdate();
	
	/**
	 * 페이징 프로젝트 리스트
	 * @param compId
	 * @param pagination
	 * @return
	 */
	public List<ProjectVO> selectProjectListPaging(String compId, PaginationInfo<ProjectVO> pagination); 
	
}
