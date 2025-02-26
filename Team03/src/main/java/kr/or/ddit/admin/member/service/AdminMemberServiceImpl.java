package kr.or.ddit.admin.member.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.admin.member.dao.AdminMemberMapper;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.UsersVO;

@Service
public class AdminMemberServiceImpl implements AdminMemberService {
	
	@Inject
	private AdminMemberMapper mapper;
	
	@Override
	public List<MemberVO> getMember() {
		return mapper.selectMember();
	}

	@Override
	public List<UsersVO> readMember(PaginationInfo<UsersVO> paging) {
		int totalRecord = 0;
		if (paging != null) {
			totalRecord = mapper.selectTotalRecord(paging);
			paging.setTotalRecord(totalRecord);
		}
		
		List<UsersVO> memList = mapper.selectMemberList(paging);
		if(!memList.isEmpty()) {
			memList.get(0).setTotal(totalRecord);
		}
		return memList;
	}

}
