package kr.or.ddit.member.info.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.file.service.BaseService;
import kr.or.ddit.member.info.dao.MemberInfoMapper;
import kr.or.ddit.vo.Cert_StatusVO;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.File_DetailVO;
import kr.or.ddit.vo.MemberVO;

@Service
public class MemberInfoServiceImpl implements MemberInfoService {

	@Inject
	MemberInfoMapper mapper;
	
	@Override
	public List<CodeVO> readMemberInfoCodeList() {
		List<CodeVO> codeList = mapper.selectMemberInfoCode();
		return codeList;
	}

	@Override
	public Cert_StatusVO readMemberCertStatus(String userId) {
		Cert_StatusVO certStatusVO = mapper.selectMemberCertStatus(userId);
		return certStatusVO;
	}

	@Override
	public MemberVO readMemberInfo(String userId) {
		MemberVO memberVO = mapper.selectMemberInfo(userId);
		return memberVO;
	}
	
	@Override
	public String readMemberEmailCheck(String memEmail) {
		String result = null;
		if(mapper.selectMemberEmailCheck(memEmail) > 0) {
			result = "fail";
		} else {
			result = "ok";
		}
		return result;
	}
	
	@Override
	public String readMemberPhoneCheck(String memHp) {
		String result = null;
		if(mapper.selectMemberPhoneCheck(memHp) > 0) {
			result = "fail";
		} else {
			result = "ok";
		}
		return result;
	}
	
	@Transactional
	@Override
	public ServiceResult modifyMemberInfo(MemberVO member) {
		List<ServiceResult> resultList = new ArrayList<>();
		ServiceResult result = null;
		
		if(mapper.updateMemberInfo(member) > 0) {
			result = ServiceResult.OK;
		} else {
			result = ServiceResult.FAIL;
		}
		resultList.add(result);
		
		if(mapper.updateMemberCertstat(member) > 0) {
			result = ServiceResult.OK;
		} else {
			result = ServiceResult.FAIL;
		}
		resultList.add(result);
		
		if(!resultList.contains(ServiceResult.FAIL)) {
			result = ServiceResult.OK;
		} else {
			result = ServiceResult.FAIL;
		}
		
		return result;
	}

}
