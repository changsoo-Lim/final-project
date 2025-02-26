package kr.or.ddit.review.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.review.dao.ReviewMapper;
import kr.or.ddit.vo.EmployVO;
import kr.or.ddit.vo.ReviewVO;

@Service
public class ReviewServiceImpl implements ReviewService {
	
	@Inject
	private ReviewMapper dao;
	
	@Override
	public ReviewVO readReviewDetail(String reviewId) {
		return dao.selectReview(reviewId);
	}

	@Override
	public List<ReviewVO> readInterviewReviewList(PaginationInfo paging) {
		if(paging!=null) {
			int totalRecord = dao.selectTotalRecord(paging);
			paging.setTotalRecord(totalRecord);
		}
		return dao.selectInterviewReviewList(paging);
	}

	@Override
	public int getTotalCount(PaginationInfo paging) {
		return dao.getTotalCount(paging);
	}

	@Override
	public List<ReviewVO> readMyReviewList(String memId, PaginationInfo paging) {
		if(paging!=null) {
			int totalRecord = dao.selectTotalRecord(paging);
			paging.setTotalRecord(totalRecord);
		}
		return dao.selectMyReviewList(memId, paging);
	}

	@Override
	public List<EmployVO> readMyApplyList(String memId) {
		return dao.selectMyApplyList(memId);
	}

	@Override
	public ServiceResult createReview(ReviewVO reviewVO) {
		if(dao.insertReview(reviewVO) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}
	
}
