package kr.or.ddit.apply.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import kr.or.ddit.apply.dao.ApplyMapper;
import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.file.service.BaseService;
import kr.or.ddit.file.service.FileService;
import kr.or.ddit.freelancer.free_offer.dao.FreeOfferMapper;
import kr.or.ddit.position.dao.PositionMapper;
import kr.or.ddit.vchat.service.VchatService;
import kr.or.ddit.vo.ApplyVO;
import kr.or.ddit.vo.CandidateVO;
import kr.or.ddit.vo.EmployVO;
import kr.or.ddit.vo.FieldVO;
import kr.or.ddit.vo.File_DetailVO;
import kr.or.ddit.vo.FilterVO;
import kr.or.ddit.vo.Free_OfferVO;
import kr.or.ddit.vo.InterviewVO;
import kr.or.ddit.vo.PositionVO;
import kr.or.ddit.vo.ProcedureVO;
import kr.or.ddit.vo.TestVO;
import lombok.RequiredArgsConstructor;

@Service("applyService")
@RequiredArgsConstructor
public class ApplyServiceImpl implements ApplyService, BaseService {

	@Inject
    private VchatService vchatService;
	
    private final ApplyMapper mapper;
    private final PositionMapper positionMapper;
    private final FileService fileService;
    
    @Value("#{dirInfo.saveDirApply}")
	private Resource saveFolderRes;
	private File saveFolder;

	@PostConstruct
	public void init() throws IOException {
		this.saveFolder = saveFolderRes.getFile();
		if(!this.saveFolder.exists()) {
			this.saveFolder.mkdirs();
		}
	}

    /** 지원자 상태 업데이트 */
    @Override
    public void updateApplicantStatus(String applyNo, String newStatus, String appPassYn) {
        mapper.updateApplicantStatus(applyNo, newStatus, appPassYn);
    }

    /** 지원 상태 코드 목록 조회 */
    @Override
    public List<ProcedureVO> getAppStatusList(Integer filedNo) {
        return mapper.selectAppStatusList(filedNo);
    }

    /** 상태 및 필드별 지원자 목록 조회 및 그룹화 */
    @Override
    public List<ApplyVO> getApplicantsGroupedByField(String status, Integer filedNo) {
    	List<ApplyVO> applicants = mapper.selectApplicantsByField(filedNo, status);
    	return applicants;
    }

    /** 다수의 지원자 불합격 처리 */
    @Override
    public boolean rejectApplicantsBatch(List<Map<String, String>> applicants) {
        int updatedCount = mapper.updateRejectApplicantsBatch(applicants);
        return updatedCount == applicants.size();
    }

    /** 특정 지원자 상세 정보 조회 */
    @Override
    public ApplyVO getApplicantDetail(Integer applyNo) {
        return mapper.selectApplicantDetail(applyNo);
    }

    /** 이미 불합격 상태인 지원자 필터링 */
    @Override
    public List<String> getAlreadyRejectedApplicants(List<Map<String, String>> applicants) {
        return applicants.stream()
            .filter(applicant -> {
                String applyNo = applicant.get("applyNo");
                List<String> statuses = mapper.getApplicantStatus(applyNo); // Mapper 호출
                return statuses.contains("N"); // 'N'이 불합격 상태
            })
            .map(applicant -> applicant.get("applyNo"))
            .collect(Collectors.toList());
    }

    /** 채용공고 리스트 조회 */
    @Override
    public List<FieldVO> getFieldListByEmployNo(Integer employNo) {
        return mapper.selectFieldListByEmployNo(employNo);
    }

    /** 채용공고 리스트 조회 */
    @Override
    public List<EmployVO> getEmployList(String compId) {
        return mapper.selectEmployList(compId);
    }
    
    @Override
    public Map<String, Object> filterApplicantsForEmail(List<Integer> applyNos) {
        // MyBatis 쿼리 호출 시 파라미터 이름을 명확히 전달
        Map<String, Object> params = new HashMap<>();
        params.put("applyNos", applyNos); // applyNos 키 설정
        List<ApplyVO> filteredApplicants = mapper.selectApplicantsForEmail(params);

        // 발송 대상 및 수신 거부 대상 분리
        Map<String, Object> result = new HashMap<>();
        List<ApplyVO> toSend = new ArrayList<>();
        List<ApplyVO> blocked = new ArrayList<>();

        for (ApplyVO applicant : filteredApplicants) {
            if ("N".equals(applicant.getStatus().getStatusEmail())) {
                blocked.add(applicant); // 수신 거부
            } else {
                toSend.add(applicant); // 발송 대상
            }
        }

        result.put("toSend", toSend);
        result.put("blocked", blocked);
        return result;
    }

    @Override
	public void removeFile(int atchFileNo, int fileSn) {
		fileService.removeFileDetail(atchFileNo, fileSn, saveFolder);
	}
	
	@Override
	public File_DetailVO download(int atchFileNo, int fileSn) {
		return Optional.ofNullable(fileService.readFileDetail(atchFileNo, fileSn, saveFolder))
						.filter(fd -> fd.getSavedFile().exists())
						.orElseThrow(() -> new RuntimeException(String.format("[%d, %d]해당 파일이 없음.", atchFileNo, fileSn)));
	}
	
	@Override
	public void insertInterview(InterviewVO interview) {
	    mapper.insertInterview(interview);
	}
	
	@Override
    public List<TestVO> getAdminTests(String testType) {
        return mapper.selectAdminTests(testType);
    }

    @Override
    public List<TestVO> getCompanyTests(String testType, String compId) {
        return mapper.selectCompanyTests(testType, compId);
    }

    @Override
    public void insertCandidate(List<CandidateVO> candidateVO) {
        for (CandidateVO candidate : candidateVO) {
            mapper.insertCandidate(candidate);
        }
    }
    
    @Override
    public void updateAppOpenYn(String applyNo, String appOpenYn) {
        mapper.updateAppOpenYn(applyNo, appOpenYn);
    }

	@Override
	public ApplyVO selectTestDetail(Integer applyNo) {
		return mapper.selectTestDetail(applyNo);
	}
	
	@Override
	public List<ApplyVO> selectInterviewDetailList(Integer applyNo) {
		return mapper.selectInterviewDetailList(applyNo);
	}

	// 공고 지원 등록
	@Override
	public ServiceResult createApply(ApplyVO applyVo) {
		ServiceResult result = null;
		ApplyVO applyCount = mapper.memApplyCheck(applyVo.getFiledNo(), applyVo.getMemId());
		Integer passCount = mapper.filterPassYN(applyVo.getFiledNo(), applyVo.getMemId());
		String procSttus = applyVo.getAppProcStatus();
		if(applyCount != null) {
			result = ServiceResult.PKDUPLICATED;
		}else {
			if(passCount != null && passCount > 0) {
				applyVo.setAppProcStatus("AP02");
			}else if(procSttus != null){
				applyVo.setAppProcStatus("AP02");
				PositionVO position = new PositionVO();
				position.setMemId(applyVo.getMemId());
				position.setFiledNo(applyVo.getFiledNo());
				position.setPositionStatusCd("OF02");
				positionMapper.updatePosition(position);
			}else {
				applyVo.setAppProcStatus("AP01");
			}
			if(mapper.insertApply(applyVo) > 0) {
				result = ServiceResult.OK;
			}else {
				result = ServiceResult.FAIL;
			}
		}
		return result;
	}
	// 공고 지원 취소
	@Override
	public int removeApply(ApplyVO applyVo) {
		return mapper.deleteApply(applyVo);
	}
//    @Override
//    public void closeEmployWithApplicantUpdates(int employNo) {
//        // 채용공고 및 모집분야 마감 처리
//        mapper.closeEmployAndFiled(employNo);
//
//        // 최종합격자 제외 모든 지원자 불합격 처리
//        mapper.rejectRemainingApplicants(employNo);
//    }

}
