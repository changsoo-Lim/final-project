package kr.or.ddit.report.dao;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.ReportVO;

@Mapper
public interface ReportMapper {
	
	/**
	 * 신고 등록
	 * @param report
	 * @return
	 */
	public int insertReport(ReportVO report);
	
	
}
