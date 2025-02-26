package kr.or.ddit.company.project.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.CompanyVO;
import kr.or.ddit.vo.Free_OfferVO;
import kr.or.ddit.vo.ProjectVO;

@Mapper
public interface CompanyProjectMapper {
	
	/**
	 * 프로젝트 전체 검색
	 * @param pagination 
	 * @return 프로젝트VO 리스트
	 */
	public List<ProjectVO> selectProjectList(String compId);
	
	/**
	 * 프로젝트 단건 검색
	 * @param projectID (검색할 프로젝트 ID)
	 * @return 프로잭트VO
	 */
	public ProjectVO selectProject(String projectID);
	
	/**
	 * 프로젝트 생성
	 * @param projectVO (생성할 프로젝트 내용)
	 * @return 성공 1, 실패0
	 */
	public int insertProject(ProjectVO projectVO);
	
	/**
	 * 프로젝트 업데이트
	 * @param projectVO (수정할 프로젝트 내용)
	 * @return 성공 1 ,실패0
	 */
	public int updateProject(ProjectVO projectVO);
	
	/**
	 * 프로젝트 제거
	 * @param projectId (제거할 프로젝트 ID)
	 * @return 성공 1, 실패0
	 */
	public int deleteProject(String project);
	
	/**
	 * 공통코드
	 * @return
	 */
	public List<CodeVO> getCode();
	
	/**
	 * 디테일용 공통코드
	 * @return
	 */
	public List<CodeVO> detailCode();
	
	/**
	 * 프로젝트 디테일 검색 
	 * @param projectId
	 * @return
	 */
	public ProjectVO selectProjcetDetail(String projectId);
	
	/**
	 * 회원 프로젝트 기업 조회
	 * @param compId
	 * @return
	 */
	public CompanyVO selectCompany(String compId);
	
	/**
	 * 유저 제안정보 조회
	 * @param username
	 * @param projectId
	 * @return
	 */
	public Free_OfferVO selectFreeOffer(@Param("memId") String username,@Param("projectNo") String projectId);
	
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
	 * @return
	 */
	public int deleteMember(@Param("projectNo") String projectNo,@Param("idList") List<String> idList);
	
	/**
	 * 컨트롤러 실행시 PostConstruct로 상태 업데이트
	 */
	public void constructUpdate();
	
	/**
	 * 토탈레코드
	 * @param compId
	 * @return
	 */
	public int selectTotalRecorde(String compId);
	
	/**
	 * 페이징 프로젝트 리스트
	 * @param compId
	 * @param pagination
	 * @return
	 */
	public List<ProjectVO> selectProjectListPaging(@Param("compId") String compId,@Param("paging") PaginationInfo<ProjectVO> pagination);

}
