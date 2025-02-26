package kr.or.ddit.users.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.users.dao.UsersMapper;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.UsersVO;

@Service
public class UsersServiceImpl implements UsersService{
    @Inject
    private UsersMapper dao;
    
    @Inject
    private PasswordEncoder encoder;
    
    
    /**
     *  유저 생성 메소드
     *  공통   : insertUser, insertUser, insertUserRole
     *  일반회원 : insertStatus
     */
	@Override
	@Transactional
	public ServiceResult createUser(UsersVO usersVO) {
		
		List<ServiceResult> resultList = new ArrayList<>();
		ServiceResult result = null;
		
		
		// 유저 데이터 등록
		if(dao.selectUser(usersVO.getUserId())==null) {
			result = dao.insertUser(usersVO) > 0 ? ServiceResult.OK : ServiceResult.FAIL;
		}else {
			return ServiceResult.PKDUPLICATED;
		}
		resultList.add(result);
		
		// 회원 데이터 등록
		if(dao.insertDetails(usersVO)>0) {
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAIL;
		}
		resultList.add(result);
		
		if(dao.insertUserRole(usersVO)>0) {
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAIL;
		}
		resultList.add(result);
		
		// 이메일/휴대폰번호 인증 여부
		if(dao.insertCertStatus(usersVO)>0) {
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAIL;
		}
		resultList.add(result);
		
		// 회원/기업 userCd로 체킁
		if ("MEMBER".equals(usersVO.getUserCd())) {
			// 회원 알림 상태 
			if(dao.insertStatus(usersVO)>0) {
				result = ServiceResult.OK;
			}else {
				result = ServiceResult.FAIL;
			}
			resultList.add(result);
        } else if ("COMPANY".equals(usersVO.getUserCd())) {
        	
        } else {
            throw new IllegalArgumentException("유저 타입: " + usersVO.getUserCd());
        }
		
		if(!resultList.contains(ServiceResult.FAIL)) {
			result = ServiceResult.OK;
		} else {
			result = ServiceResult.FAIL;
		}
		
		return result;
	}

	@Override
	public UsersVO readUser(String userId) {
		return dao.selectUser(userId);
	}
	
	// 비밀번호 재설정
	@Override
	public ServiceResult modifyPassword(UsersVO user) {
		int cnt = dao.updatePassword(user);
		ServiceResult result;
		if(cnt>0) {
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAIL;
		}
		return result;
	}

	@Override
	public List<CodeVO> readCodeGender() {
		return dao.selectCodeGender();
	}

	@Override
	public List<CodeVO> readCompanySizeCode() {
		return dao.selectCompanySizeCode();
	}

	@Override
	public List<CodeVO> readIndustryCode() {
		return dao.selectIndustryCode();
	}

	@Override
	public UsersVO readPass(String userId) {
		return dao.selectPass(userId);
	}
	
	// 비밀번호 체크
	@Override
	public boolean checkPassword(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }

	@Override
	public ServiceResult removeUser(UsersVO user) {
		int cnt = dao.deleteUser(user);
		ServiceResult result;
		if(cnt>0) {
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAIL;
		}
		return result;
	}
	
}