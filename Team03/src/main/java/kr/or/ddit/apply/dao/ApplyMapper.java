package kr.or.ddit.apply.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.vo.ApplyVO;
import kr.or.ddit.vo.CandidateVO;
import kr.or.ddit.vo.EmployVO;
import kr.or.ddit.vo.FieldVO;
import kr.or.ddit.vo.FilterVO;
import kr.or.ddit.vo.InterviewVO;
import kr.or.ddit.vo.ProcedureVO;
import kr.or.ddit.vo.TestVO;

@Mapper
public interface ApplyMapper {
	/**
     * 지원자 등록
     */
    public ApplyVO memApplyCheck(@Param("fieldNo") int fieldNo, @Param("memId") String memId);
    /**
     * 지원자 등록
     */
    public int insertApply(ApplyVO applyVo);
    /**
     * 지원 취소
     */
    public int deleteApply(ApplyVO applyVo);
    /**
     * 필터 조건 충족 여부
     */
    public Integer filterPassYN(@Param("fieldNo") int fieldNo, @Param("memId") String memId);
    /**
     * 모집분야 번호로 필터 조건 조회
     * @param fieldNo
     * @return List<FilterVO>
     */
    public List<FilterVO> selectFilterList(int fieldNo);
	
    /**
     * 지원자 상태 업데이트
     * @param applyNo 지원자 ID
     * @param newStatus 새로운 상태 코드
     * @param appPassYn 합격 여부 (Y/N)
     */
	public void updateApplicantStatus(
        @Param("applyNo") String applyNo, 
        @Param("newStatus") String newStatus, 
        @Param("appPassYn") String appPassYn
    );

    /**
     * 모집 분야 및 상태에 따른 지원자 목록 조회
     * @param filedNo 모집 분야 번호 (선택 사항)
     * @param status 상태 코드 (선택 사항)
     * @return 모집 분야 및 상태별 지원자 목록
     */
	public List<ApplyVO> selectApplicantsByField(
        @Param("filedNo") Integer filedNo, 
        @Param("status") String status
    );

    /**
     * 지원 상태 코드 목록 조회
     * @return 지원 상태 코드 목록
     */
	public List<ProcedureVO> selectAppStatusList(Integer filedNo);

    /**
     * 다수 지원자 불합격 처리
     * @param applicants 지원자 정보 리스트 (applyNo와 currentStatus 포함)
     * @return 업데이트된 행의 개수
     */
	public int updateRejectApplicantsBatch(@Param("applicants") List<Map<String, String>> applicants);

    /**
     * 특정 지원자 상세 정보 조회
     * @param applyNo 지원자 ID
     * @return 지원자 상세 정보
     */
	public ApplyVO selectApplicantDetail(@Param("applyNo") Integer applyNo);

    /**
     * 특정 지원자의 상태 조회
     * @param applyNo 지원자 ID
     * @return 지원자 상태 (APP_PASS_YN)
     */
	public List<String> getApplicantStatus(@Param("applyNo") String applyNo);

    /**
     * 특정 채용공고에 포함된 모집 분야 리스트 조회
     * @param employNo 채용공고 번호
     * @return 모집 분야 리스트
     */
	public List<FieldVO> selectFieldListByEmployNo(@Param("employNo") Integer employNo);

    /**
     * 채용공고 리스트 조회
     * @return 채용공고 리스트
     */
	public List<EmployVO> selectEmployList(@Param("compId")String compId);

	public List<ApplyVO> selectApplicantsForEmail(Map<String, Object> params);

	public List<String> selectApplicantEmailsByFieldNo(Integer filedNo);

	public void insertInterview(InterviewVO interview);

	public List<TestVO> selectTestsByCompId(String compId);

	public List<TestVO> selectAllTests();

	public List<TestVO> selectAdminTests(@Param("testType") String testType);

	public List<TestVO> selectCompanyTests(@Param("testType") String testType, @Param("compId") String compId);

	public void insertCandidate(CandidateVO candidateVO);

	public void updateAppOpenYn(@Param("applyNo") String applyNo,@Param("appOpenYn")String appOpenYn);

	/** 지원자번호가지고 테스트 정보 가져오기
	 * @param applyNo
	 * @return
	 */
	public ApplyVO selectTestDetail(Integer applyNo);

	public List<ApplyVO> selectInterviewDetailList(Integer applyNo);
	
//	public void closeEmployAndFiled(int employNo);
//
//	public void rejectRemainingApplicants(int employNo);


}
