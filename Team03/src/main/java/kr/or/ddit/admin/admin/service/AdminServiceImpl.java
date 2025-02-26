package kr.or.ddit.admin.admin.service;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.admin.admin.dao.AdminMapper;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.CompanyVO;

@Service
public class AdminServiceImpl implements AdminService {
	@Inject
	AdminMapper dao;

	@Override
	public List<CompanyVO> readCompanyList(PaginationInfo paging) {
		if (paging != null) {
			int totalRecord = dao.selectTotalRecord(paging);
			paging.setTotalRecord(totalRecord);
		}
		return dao.selectCompanyList(paging);
	}
	@Override
	public CompanyVO readCompany(String compId) {
		return dao.selecteCompany(compId);
	}
	
	@Override
	public List<Map<String, Object>> monthUserList() {
		return dao.monthUserList();
	}
	@Override
	public List<Map<String, Object>> monthEmployList() {
		return dao.monthEmployList();
	}
	@Override
	public List<Map<String, Object>> sidoList() {
		return dao.sidoList();
	}
	@Override
	public List<Map<String, Object>> reportList() {
		return dao.reportList();
	}
	@Override
	public List<Map<String, Object>> reviewList() {
		return dao.reviewList();
	}
	@Override
	public List<Map<String, Object>> noticeList() {
		return dao.noticeList();
	}
	@Override
	public List<Map<String, Object>> mountAmountList() {
		return dao.mountAmountList();
	}
	@Override
	public List<Map<String, Object>> prodAmountList() {
		return dao.prodAmountList();
	}
	@Override
	public List<Map<String, Object>> totalList() {
		return dao.totalList();
	}
}
