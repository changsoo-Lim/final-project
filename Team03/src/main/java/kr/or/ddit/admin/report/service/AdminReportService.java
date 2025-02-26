package kr.or.ddit.admin.report.service;

import java.util.List;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.ReportVO;

public interface AdminReportService {
	
	public ReportVO readAdminReport(int reportNo);
	
	public List<ReportVO> readAdminReportList(PaginationInfo paging);
	
	public ServiceResult removeAdminReport(int reportNo);
}
