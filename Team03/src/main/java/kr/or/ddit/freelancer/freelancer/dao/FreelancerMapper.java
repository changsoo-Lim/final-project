package kr.or.ddit.freelancer.freelancer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.FreelancerVO;
import kr.or.ddit.vo.FreeskillsVO;

@Mapper
public interface FreelancerMapper {
	/**
	 * 프리랜서 상세 조회
	 * @return FreelancerVO
	 */
	public FreelancerVO selectFreelancer(String memId);
	/**
	 * 프리랜서 리스트 조회
	 * @param paging
	 * @return
	 */
	public List<FreelancerVO> selectFreelancerList(PaginationInfo<FreelancerVO> paging);
	/**
	 * 목록 수 조회
	 * @param paging
	 * @return
	 */
	public int selectTotalRecord(PaginationInfo<FreelancerVO> paging);
	/**
	 * 프리랜서 등록
	 * @param freelancer
	 * @return
	 */
	public int insertFreelancer(FreelancerVO freelancer);
	/**
	 * 프리랜서 정보 수정
	 * @param freelancer
	 * @return
	 */
	public int updateFreelancer(FreelancerVO freelancer);
	/**
	 * 프리랜서 등록(프리랜서상태 'Y'으로 수정)
	 * @param memId
	 * @return
	 */
	public int updateMemberToFreelancer(String memId);
	/**
	 * 프리랜서 등록 삭제(프리랜서상태 'N'으로 수정)
	 * @param memId
	 * @return
	 */
	public int deleteFreelancer(String memId);
	
	/**
	 * 공통코드 리스트 출력
	 * @return CodeVO
	 */
	public List<CodeVO> selectCodeList();
	
	/**
	 * 프리랜서 보유스킬 등록
	 * @param freeskills
	 * @return
	 */
	public int insertFreeskills(FreeskillsVO freeskills);
	/**
	 * 프리랜서 보유스킬 삭제
	 * @param freeskills
	 * @return
	 */
	public int deleteFreeskills(String memId);
}
