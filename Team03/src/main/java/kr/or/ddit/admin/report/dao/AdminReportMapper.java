package kr.or.ddit.admin.report.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.ReportVO;
@Mapper
public interface AdminReportMapper {
	
	/**
	 * 신고 목록 조회
	 * @param paging
	 * @return
	 */
	public List<ReportVO> selectAdminReportList(PaginationInfo paging);
	/**
	 * 페이징 처리를 위한 검색 결과 레코드 수 조회
	 * @param paging
	 * @return
	 */
	public int selectTotalRecord(PaginationInfo paging);
	/**
	 * 신고 상세목록 조회
	 * @param reportNo
	 * @return
	 */
	public ReportVO selectAdminReport(int reportNo);
	
	/**
	 * 신고 삭제
	 * @param reportNo
	 * @return
	 */
	public int deleteAdminReport(int reportNo);
}
