package kr.or.ddit.admin.review.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.admin.review.dao.AdminReviewMapper;
import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.ReviewVO;

@Service
public class AdminReviewServieImpl implements AdminReviewService {

	@Inject
	private AdminReviewMapper mapper;
	
	@Override
	public List<ReviewVO> readReviewList(PaginationInfo paging) {
		if(paging!=null) {
			int totalRecord = mapper.selectTotalRecord(paging);
			paging.setTotalRecord(totalRecord);
		}
		return mapper.selectReviewList(paging);
	}

	@Override
	public int getTotalCount(PaginationInfo paging) {
		return mapper.getTotalCount(paging);
	}

	@Override
	public ServiceResult modifiyReviewStatus(int reviewNo) {
		if(mapper.updateReviewStatus(reviewNo) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

}
