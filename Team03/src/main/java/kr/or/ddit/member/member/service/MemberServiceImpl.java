package kr.or.ddit.member.member.service;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import kr.or.ddit.member.member.dao.MemberMapper;
import kr.or.ddit.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {
	@Inject
	MemberMapper mapper;
	
	@Override
	public String readPhoneMember(String memHp) {
		MemberVO member = mapper.selectPhoneMember(memHp);
		// 회원이 검색 성공
		if (member != null && StringUtils.isNotBlank(member.getMemId())) {
	        return member.getMemId();
	    }
		return "fail";
	}
}
