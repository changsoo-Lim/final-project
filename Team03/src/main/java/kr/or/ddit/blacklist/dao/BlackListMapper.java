package kr.or.ddit.blacklist.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.vo.BlacklistVO;

@Mapper
public interface BlackListMapper {
	
	public int insertBlacklist(BlacklistVO black);
	
	public BlacklistVO selectBlacklist(int blacklistNo);
	
	public List<BlacklistVO> selectBlacklistList(PaginationInfo paging);
	
	public int selectTotalRecord(PaginationInfo paging);
	
	public int updateBlacklist(BlacklistVO black);
	
	public int deleteBlacklist(int blacklistNo);
	
	public int getTotalCount(PaginationInfo paging);
	
	public int updateMemberStatus(String memId);
	
	public int updateMemberStatusToNormal(String memId);
	
	public int deleteBlacklists(List<Integer> blacklistNo);

	public List<BlacklistVO> selectBlacklists(List<Integer> blacklistNo);
}
