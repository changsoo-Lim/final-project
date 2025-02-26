package kr.or.ddit.admin.report.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.admin.report.dao.AdminReportMapper;
import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.commons.exception.PKNotFoundException;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.ReportVO;

@Service
public class AdminReportServiceImpl implements AdminReportService {
	
	@Inject
	private AdminReportMapper mapper;

	@Override
	public ReportVO readAdminReport(int reportNo) {
		ReportVO report = mapper.selectAdminReport(reportNo);
		if(report == null)
			throw new PKNotFoundException(String.format("%s  글이 없음", reportNo));
		return report;
	}

	@Override
	public List<ReportVO> readAdminReportList(PaginationInfo paging) {
		if(paging!=null) {
			int totalRecord = mapper.selectTotalRecord(paging);
			paging.setTotalRecord(totalRecord);
		}
		return mapper.selectAdminReportList(paging);
	}

	@Override
	public ServiceResult removeAdminReport(int reportNo) {
		int rows = mapper.deleteAdminReport(reportNo);
		return rows > 0 ? ServiceResult.OK : ServiceResult.FAIL;
	}
	
}
