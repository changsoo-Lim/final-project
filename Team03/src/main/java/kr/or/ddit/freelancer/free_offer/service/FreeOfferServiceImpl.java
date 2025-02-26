package kr.or.ddit.freelancer.free_offer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.freelancer.free_offer.dao.FreeOfferMapper;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.Free_OfferVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FreeOfferServiceImpl implements FreeOfferService {
	
	private final FreeOfferMapper mapper;
	
	@Override
	public Free_OfferVO readFreeOffer(String memId, int projectNo) {
		return mapper.selectFreeOffer(memId, projectNo);
	}

	@Override
	public List<Free_OfferVO> memReadFreeOfferList(PaginationInfo<Free_OfferVO> paging, String memId) {
		paging.setTotalRecord(mapper.memSelectTotalRecord(paging, memId));
		List<Free_OfferVO> freeOfferList = mapper.memSelectFreeOfferList(paging, memId);
		return freeOfferList;
	}

	@Override
	public List<Free_OfferVO> compReadFreeOfferList(PaginationInfo<Free_OfferVO> paging, String companyId) {
		paging.setTotalRecord(mapper.compSelectTotalRecord(paging, companyId));
		List<Free_OfferVO> freeOfferList = compReadFreeOfferList(paging, companyId);
		return freeOfferList;
	}

	@Override
	public ServiceResult createFreeOffer(Free_OfferVO freeOffer) {
		ServiceResult result = null;
		if(mapper.selectFreeOffer(freeOffer.getMemId(), freeOffer.getProjectNo())!=null) {
			result = ServiceResult.PKDUPLICATED;
		}else if(mapper.insertFreeOffer(freeOffer) > 0) {
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAIL;
		}
		return result;
	}

	@Override
	public ServiceResult modifyFreeOffer(Free_OfferVO freeOffer) {
		ServiceResult result = null;
		if(mapper.updateFreeOffer(freeOffer) > 0) {
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAIL;
		}
		return result;
	}
	@Override
	public List<CodeVO> selectCodeList() {
		List<CodeVO> codeList = mapper.selectCodeList();
		return codeList;
	}

	@Override
	public ServiceResult acceptOffer(Free_OfferVO freeOffer) {
		int cnt = mapper.acceptOffer(freeOffer);
		return cnt > 0 ? ServiceResult.OK : ServiceResult.FAIL;
	}

	@Override
	public ServiceResult rejectOffer(Free_OfferVO freeOffer) {
		int cnt = mapper.rejectOffer(freeOffer);
		return cnt > 0 ? ServiceResult.OK : ServiceResult.FAIL;
	}
}
