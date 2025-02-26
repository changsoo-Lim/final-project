package kr.or.ddit.company.company.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.company.benefit.dao.BenefitMapper;
import kr.or.ddit.company.company.dao.CompanyMapper;
import kr.or.ddit.company.project.service.CompanyProjectService;
import kr.or.ddit.employ.employ.dao.EmployMapper;
import kr.or.ddit.employ.employ.service.EmployService;
import kr.or.ddit.file.service.FileService;
import kr.or.ddit.review.dao.ReviewMapper;
import kr.or.ddit.review.service.ReviewService;
import kr.or.ddit.vo.Cmp_BftVO;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.CompanyVO;
import kr.or.ddit.vo.EmployVO;
import kr.or.ddit.vo.ProjectVO;
import kr.or.ddit.vo.ReviewVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
	
	@Inject
	private CompanyMapper dao;
	@Inject
	private BenefitMapper bftDao;
	@Inject
	private ReviewMapper reviewDao;
	@Inject
	private EmployMapper empMapper;
	@Inject
	private ReviewMapper reMapper;
	@Inject
	private CompanyProjectService proMapper;
	
	private final FileService fileService;
	
	@Value("#{dirInfo.saveDirMemberImage}")
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
	public CompanyVO readCompany(String compId) {
		return dao.selectCompany(compId);
	}

	@Override
	public ServiceResult removeCompany(String compId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceResult modifyCompContent(CompanyVO compVO) {
		int cnt = dao.updateCompContent(compVO);
		return cnt > 0 ? ServiceResult.OK : ServiceResult.FAIL;
	}
	@Override
	public List<CodeVO> readCode() {
		return dao.selecteCode();
	}

	@Override
	public String readCompMobile(String compMobile) {
		CompanyVO company = dao.selectCompMobile(compMobile);
		// 기업 회원이 있으면 회원아이디 리턴
		if (company != null && StringUtils.isNotBlank(company.getCompId())) {
			return company.getCompId();
		}
		return "fail";
	}

	@Override
	public ServiceResult modifyInfo(CompanyVO company) {
		int cnt = dao.updateInfo(company);
		return cnt > 0 ? ServiceResult.OK : ServiceResult.FAIL;
	}
	
	@Override
	public ServiceResult updateCompanyImage(CompanyVO company) {
		Integer atchFileNo = Optional.ofNullable(company.getFile())
				.filter(af->! CollectionUtils.isEmpty(af.getFileDetails()))
				.map(af->{
					fileService.createFile(af, saveFolder);
					return af.getAtchFileNo();
				}).orElse(null);
		company.setAtchFileNo(atchFileNo);
		if(dao.updateCompanyImage(company) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public List<EmployVO> selectEmpList(String username) {
		return dao.selectCompEmpList(username);
	}

	@Override
	public List<Cmp_BftVO> selectBenefitList(String username) {
		return bftDao.selectCompBftList(username);
	}

	@Override
	public List<ReviewVO> selectReviewList(String username) {
		return reMapper.selectReviewList(username);
	}

	@Override
	public List<ProjectVO> selectProjectList(String username) {
		return proMapper.selectProjectList(username);
	}
}
