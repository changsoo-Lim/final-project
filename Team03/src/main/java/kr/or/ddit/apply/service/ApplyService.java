package kr.or.ddit.apply.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.vo.ApplyVO;
import kr.or.ddit.vo.CandidateVO;
import kr.or.ddit.vo.EmployVO;
import kr.or.ddit.vo.FieldVO;
import kr.or.ddit.vo.InterviewVO;
import kr.or.ddit.vo.ProcedureVO;
import kr.or.ddit.vo.TestVO;
import kr.or.ddit.vo.VchatVO;

public interface ApplyService {

    /**
     * 지원자 등록
     */
    public ServiceResult createApply(ApplyVO applyVo);
    /**
     * 지원 취소
     */
    public int removeApply(ApplyVO applyVo);
    
    /**
     * 지원 상태 코드 목록 조회
     * @return 지원 상태 코드 목록
     */
    public List<ProcedureVO> getAppStatusList(Integer filedNo);

    /**
     * 지원자 상태 업데이트
     * @param applyNo 지원자 ID
     * @param newStatus 새로운 상태 코드
     * @param appPassYn 합격 여부 (Y/N)
     */
    public void updateApplicantStatus(String applyNo, String newStatus, String appPassYn);

    /**
     * 상태 또는 필드에 따라 지원자 목록 그룹화하여 조회
     * @param status 상태 코드 (선택 사항)
     * @param filedNo 모집 분야 번호 (선택 사항)
     * @return 상태 및 필드별로 그룹화된 지원자 목록
     */
    public List<ApplyVO> getApplicantsGroupedByField(String status, Integer filedNo);

    /**
     * 다수의 지원자 불합격 처리
     * @param applicants 지원자 정보 리스트 (applyNo, currentStatus 포함)
     * @return 처리 결과 (true: 성공, false: 일부 실패)
     */
    public boolean rejectApplicantsBatch(List<Map<String, String>> applicants);

    /**
     * 특정 지원자의 상세 정보 조회
     * @param applyNo 지원자 번호
     * @return 지원자의 상세 정보
     */
    public ApplyVO getApplicantDetail(Integer applyNo);

    /**
     * 이미 불합격 상태인 지원자 필터링
     * @param applicants 지원자 정보 리스트
     * @return 이미 불합격 상태인 지원자 ID 목록
     */
    public List<String> getAlreadyRejectedApplicants(List<Map<String, String>> applicants);

    /**
     * 채용공고 리스트 조회
     * @param compId 
     * @return 채용공고 리스트
     */
    public List<EmployVO> getEmployList(String compId);

    /**
     * 특정 채용공고에 해당하는 모집 분야 리스트 조회
     * @param employNo 채용공고 번호
     * @return 해당 채용공고에 포함된 모집 분야 리스트
     */
    public List<FieldVO> getFieldListByEmployNo(Integer employNo);

	public Map<String, Object> filterApplicantsForEmail(List<Integer> applyNos);

	public void insertInterview(InterviewVO interview);

	public List<TestVO> getAdminTests(String testType);

	public List<TestVO> getCompanyTests(String testType, String compId);

	public void insertCandidate(List<CandidateVO> candidateVO);

	public void updateAppOpenYn(String applyNo, String appOpenYn);

	public ApplyVO selectTestDetail(Integer applyNo);

	public List<ApplyVO> selectInterviewDetailList(Integer applyNo);

	

//	public void closeEmployWithApplicantUpdates(int employNo);
	
}
