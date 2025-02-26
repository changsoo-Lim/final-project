package kr.or.ddit.report.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.report.dao.ReportMapper;
import kr.or.ddit.vo.ReportVO;

@Service
public class ReportServiceImpl implements ReportService {
	
	@Inject
	private ReportMapper mapper;
	
	
	@Override
	public ServiceResult reportBoard(ReportVO report) {
		int rows = mapper.insertReport(report);
		return rows > 0 ? ServiceResult.OK : ServiceResult.FAIL;
	}


	

}
