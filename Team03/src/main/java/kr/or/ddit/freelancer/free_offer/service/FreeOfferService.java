package kr.or.ddit.freelancer.free_offer.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.Free_OfferVO;

public interface FreeOfferService {
	/**
	 * 단건조회 : 프리랜서 제안 상세 조회
	 * @param memId
	 * @param projectNo
	 * @return
	 */
	public Free_OfferVO readFreeOffer(@Param("memId") String memId, @Param("projectNo") int projectNo);
	/**
	 * 일반회원 다건조회 : 제안 받은 목록 조회
	 * @return
	 */
	public List<Free_OfferVO> memReadFreeOfferList(PaginationInfo<Free_OfferVO> paging, String memId);
	/**
	 * 기업회원 다건조회 : 제안한 목록 조회
	 * @param companyId
	 * @return
	 */
	public List<Free_OfferVO> compReadFreeOfferList(PaginationInfo<Free_OfferVO> paging, String companyId);
	/**
	 * 등록 : 프리랜서제안 등록
	 * @param freeOffer
	 * @return
	 */
	public ServiceResult createFreeOffer(Free_OfferVO freeOffer);
	/**
	 * 수정 : 프리랜서 상태코드변경(OF02:수락, OF03:거절, OF04:제안취소)
	 * @param freeOffer
	 * @return
	 */
	public ServiceResult modifyFreeOffer(Free_OfferVO freeOffer);
	/**
	 * 공통코드 리스트 출력
	 * @return CodeVO
	 */
	public List<CodeVO> selectCodeList();
	
	/**
	 * 프로젝트 제안 수락 업데이트
	 * @param freeOffer
	 * @return
	 */
	public ServiceResult acceptOffer(Free_OfferVO freeOffer);
	
	/**
	 * 프로젝트 제안 거절 업데이트
	 * @param freeOffer
	 * @return
	 */
	public ServiceResult rejectOffer(Free_OfferVO freeOffer);
}
