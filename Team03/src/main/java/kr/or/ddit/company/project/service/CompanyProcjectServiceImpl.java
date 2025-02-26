package kr.or.ddit.company.project.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.company.project.dao.CompanyProjectMapper;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.CompanyVO;
import kr.or.ddit.vo.Free_OfferVO;
import kr.or.ddit.vo.FreelancerVO;
import kr.or.ddit.vo.ProjectVO;

@Service
public class CompanyProcjectServiceImpl implements CompanyProjectService {
	
	@Inject
	private CompanyProjectMapper mapper;
	
	@Override
	public List<CodeVO> getCode() {
		return mapper.getCode();
	}

	@Override
	public ServiceResult insertProject(ProjectVO project) {
		int cnt = mapper.insertProject(project);
		return cnt > 0 ? ServiceResult.OK : ServiceResult.FAIL;
	}

	@Override
	public List<ProjectVO> selectProjectListOneComp(PaginationInfo<FreelancerVO> paging, String compId) {
		return mapper.selectProjectList(compId);
	}

	@Override
	public List<ProjectVO> selectProjectList(String compId) {
		return mapper.selectProjectList(compId);
	}

	@Override
	public ProjectVO selectProjectOne(String projectId) {
		return mapper.selectProject(projectId);
	}

	@Override
	public ServiceResult updateProject(ProjectVO project) {
		int cnt = mapper.updateProject(project);
		return cnt > 0 ? ServiceResult.OK : ServiceResult.FAIL;
	}

	@Override
	public ServiceResult deleteProject(String project) {
		
		String[] proIdList = project.split(",");
		int cnt = 0;
		for (String proId : proIdList) {
			mapper.deleteProject(proId);
			cnt++;
		}
		
		return cnt > 0 ? ServiceResult.OK : ServiceResult.FAIL;
	}

	@Override
	public List<CodeVO> detailCode() {
		return mapper.detailCode();
	}

	@Override
	public ProjectVO selectProjectDetail(String projectId) {
		return mapper.selectProjcetDetail(projectId);
	}

	@Override
	public CompanyVO selectCompay(String compId) {
		return mapper.selectCompany(compId);
	}

	@Override
	public Free_OfferVO selectFreeOffe(String username, String projectId) {
		return mapper.selectFreeOffer(username,projectId);
	}

	@Override
	public ProjectVO selectCompnyProjectDetail(String projectId) {
		return mapper.selectCompnyProjectDetail(projectId);
	}

	@Override
	public ServiceResult deleteMember(String projectNo, List<String> idList) {
		int cnt = mapper.deleteMember(projectNo, idList); 
		return cnt > 0 ? ServiceResult.OK : ServiceResult.FAIL;
	}

	@Override
	public void constructUpdate() {
		mapper.constructUpdate();
		
	}
	
	@Override
	public List<ProjectVO> selectProjectListPaging(String compId, PaginationInfo<ProjectVO> pagination) {
		int totalRecord = 0;
		if (pagination != null) {
			totalRecord = mapper.selectTotalRecorde(compId);
			pagination.setTotalRecord(totalRecord);
		}
		
		List<ProjectVO> proList = mapper.selectProjectListPaging(compId, pagination);
		if(!proList.isEmpty()) {
			proList.get(0).setTotal(totalRecord);
		}
		return proList;
	}

}
