package kr.or.ddit.admin.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.CompanyVO;

@Mapper
public interface AdminMapper {

	public List<CompanyVO> selectCompanyList(PaginationInfo paging);

	public CompanyVO selecteCompany(String compId);

	public int selectTotalRecord(PaginationInfo paging);

	/**
	 * 년월 별 신규 가입자 및 구직자,기업 회원 수
	 * @return
	 */
	public List<Map<String, Object>> monthUserList();

	/**
	 * 년월 별 채용공고 수
	 * @return
	 */
	public List<Map<String, Object>> monthEmployList();
	
	/**
	 * 년월 별 지역별 공고 수
	 * @return
	 */
	public List<Map<String, Object>> sidoList();
	
	/**
	 * 신고 당한 회원 중 처리가 안된 회원리스트
	 * @return
	 */
	public List<Map<String, Object>> reportList();
	
	/**
	 * 면접 후기 승인 처리가 안된 회원리스트
	 * @return
	 */
	public List<Map<String, Object>> reviewList();
	
	/**
	 * 문의 답변 처리가 안된 회원리스트
	 * @return
	 */
	public List<Map<String, Object>> noticeList();
	
	/**
	 * 월별집계 리스트
	 * @return
	 */
	public List<Map<String, Object>> mountAmountList();
	
	/**
	 * 메인공고 상품별 집계 리스트
	 * @return
	 */
	public List<Map<String, Object>> prodAmountList();
	
	/**
	 * 총 매출 리스트
	 * @return
	 */
	public List<Map<String, Object>> totalList();
}
