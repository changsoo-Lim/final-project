package kr.or.ddit.report.service;

import java.util.List;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.ReportVO;

public interface ReportService {
	
	/**
	 * 게시글 신고
	 * @param report
	 * @return
	 */
	public ServiceResult reportBoard(ReportVO report);
	
	
}
