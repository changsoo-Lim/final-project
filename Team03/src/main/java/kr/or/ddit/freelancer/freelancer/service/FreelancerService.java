package kr.or.ddit.freelancer.freelancer.service;

import java.util.List;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.File_DetailVO;
import kr.or.ddit.vo.FreelancerVO;

public interface FreelancerService {
	/**
	 * 프리랜서 상세 조회
	 * @return FreelancerVO
	 */
	public FreelancerVO readFreelancer(String memId);
	/**
	 * 프리랜서 리스트 조회
	 * @param paging
	 * @return
	 */
	public List<FreelancerVO> readFreelancerList(PaginationInfo<FreelancerVO> paging);
	/**
	 * 프리랜서 등록
	 * @param freelancer
	 * @return
	 */
	public ServiceResult createFreelancer(FreelancerVO freelancer);
	/**
	 * 프리랜서 정보 수정
	 * @param freelancer
	 * @return
	 */
	public ServiceResult modifyFreelancer(FreelancerVO freelancer);
	/**
	 * 프리랜서 등록(프리랜서상태 'Y'으로 수정)
	 * @param memId
	 * @return
	 */
	public ServiceResult modifyMemberToFreelancer(String memId);
	/**
	 * 프리랜서 등록 삭제(프리랜서상태 'N'으로 수정)
	 * @param memId
	 * @return
	 */
	public ServiceResult removeFreelancer(String memId);
	/**
	 * 공통코드 리스트 출력
	 * @return CodeVO
	 */
	public List<CodeVO> selectCodeList();
	
	/**
	 * 파일 다운로드
	 * 
	 * @param atchFileId
	 * @param fileSn
	 * @return
	 */
	public File_DetailVO download(int atchFileNo, int fileSn);

	/**
	 * 파일 한건 삭제
	 * 
	 * @param atchFileId
	 * @param fileSn
	 */
	public void removeFile(int atchFileNo, int fileSn);
}
