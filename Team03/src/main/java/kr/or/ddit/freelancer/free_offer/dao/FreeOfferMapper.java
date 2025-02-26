package kr.or.ddit.freelancer.free_offer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.Free_OfferVO;
import kr.or.ddit.vo.FreelancerVO;

@Mapper
public interface FreeOfferMapper {
	/**
	 * 단건조회 : 프리랜서 제안 상세 조회
	 * @param memId
	 * @param projectNo
	 * @return
	 */
	public Free_OfferVO selectFreeOffer(@Param("memId") String memId, @Param("projectNo") int projectNo);
	/**
	 * 일반회원 다건조회 목록 수 조회
	 * @param paging
	 * @return
	 */
	public int memSelectTotalRecord(@Param("paging") PaginationInfo<Free_OfferVO> paging, @Param("memId") String memId);
	/**
	 * 기업회원 다건조회 목록 수 조회
	 * @param paging
	 * @return
	 */
	public int compSelectTotalRecord(@Param("paging") PaginationInfo<Free_OfferVO> paging, @Param("companyId") String companyId);
	/**
	 * 일반회원 다건조회 : 제안 받은 목록 조회
	 * @return
	 */
	public List<Free_OfferVO> memSelectFreeOfferList(@Param("paging") PaginationInfo<Free_OfferVO> paging, @Param("memId") String memId);
	/**
	 * 기업회원 다건조회 : 제안한 목록 조회
	 * @param companyId
	 * @return
	 */
	public List<Free_OfferVO> compSelectFreeOfferList(@Param("paging") PaginationInfo<Free_OfferVO> paging, @Param("companyId") String companyId);
	/**
	 * 등록 : 프리랜서제안 등록
	 * @param freeOffer
	 * @return
	 */
	public int insertFreeOffer(Free_OfferVO freeOffer);
	/**
	 * 수정 : 프리랜서 상태코드변경(OF02:수락, OF03:거절, OF04:제안취소)
	 * @param freeOffer
	 * @return
	 */
	public int updateFreeOffer(Free_OfferVO freeOffer);
	/**
	 * 공통코드 리스트 출력
	 * @return CodeVO
	 */
	public List<CodeVO> selectCodeList();
	
	/**
	 * 프로젝트 수락 업데이트
	 * @param freeOffer
	 * @return
	 */
	public int acceptOffer(Free_OfferVO freeOffer);
	
	/**
	 * 프로젝트 거절 업데이트
	 * @param freeOffer
	 * @return
	 */
	public int rejectOffer(Free_OfferVO freeOffer);
	
}
