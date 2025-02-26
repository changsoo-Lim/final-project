package kr.or.ddit.blacklist.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.blacklist.dao.BlackListMapper;
import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.vo.BlacklistVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlackListServiceImpl implements BlackListService {
	@Inject
	private final BlackListMapper mapper;
	
	@Override
	public ServiceResult createBlacklist(BlacklistVO black) {
		// 1. 회원 상태를 'B'로 변경
	    int updatedRows = mapper.updateMemberStatus(black.getMemId());
	    if (updatedRows > 0) {
	        // 2. 블랙리스트 등록
	        if (mapper.insertBlacklist(black) > 0) {
	            return ServiceResult.OK;
	        } else {
	            return ServiceResult.FAIL;
	        }
	    } else {
	        return ServiceResult.FAIL; // 회원 상태 변경 실패
	    }
	}

	@Override
	public List<BlacklistVO> readBlacklistList(PaginationInfo paging) {
		if(paging!=null) {
			int totalRecord = mapper.selectTotalRecord(paging);
			paging.setTotalRecord(totalRecord);
		}
		return mapper.selectBlacklistList(paging);
	}

	@Override
	public ServiceResult modifyBlacklist(BlacklistVO black) {
			if( mapper.updateBlacklist(black) > 0) {
						return ServiceResult.OK; 
					}else {
						return	ServiceResult.FAIL;
					}
	}

	@Override
	public ServiceResult removeBlacklist(int blacklistNo) {
		// 1. 블랙리스트 삭제
	    BlacklistVO black = mapper.selectBlacklist(blacklistNo); // 삭제 전 MEM_ID 가져오기
	    if (mapper.deleteBlacklist(blacklistNo) > 0) {
	        // 2. 삭제된 MEM_ID의 상태를 'N'으로 변경
	        mapper.updateMemberStatusToNormal(black.getMemId());
	        return ServiceResult.OK;
	    } else {
	        return ServiceResult.FAIL;
	    }
	}
	
	@Override
	public ServiceResult removeBlacklists(List<Integer> blacklistNos) {
	    // 삭제 대상의 BlacklistVO 리스트를 가져옴
	    List<BlacklistVO> blacklists = mapper.selectBlacklists(blacklistNos);
	    
	    // 블랙리스트 삭제
	    int count = mapper.deleteBlacklists(blacklistNos);
	    if (count > 0) {
	        // 각 블랙리스트의 회원 상태를 'N'으로 변경
	        for (BlacklistVO black : blacklists) {
	            mapper.updateMemberStatusToNormal(black.getMemId());
	        }
	        return ServiceResult.OK;
	    } else {
	        return ServiceResult.FAIL;
	    }
	}
	

	@Override
	public int getTotalCount(PaginationInfo paging) {
		return mapper.getTotalCount(paging);
	}

}
