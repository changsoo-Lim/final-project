package kr.or.ddit.blacklist.service;

import java.util.List;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.vo.BlacklistVO;

public interface BlackListService {
	
	/**
	 * 블랙리스트 등록
	 * @param blacklist
	 * @return
	 */
	public ServiceResult createBlacklist(BlacklistVO black);
	/**
	 * 블랙리스트 목록
	 * @param paging
	 * @return
	 */
	public List<BlacklistVO> readBlacklistList(PaginationInfo paging);
	/**
	 * 블랙리스트 수정
	 * @param blacklist
	 * @return
	 */
	public ServiceResult modifyBlacklist(BlacklistVO black);
	/**
	 * 블랙리스트 삭제(화이트리스트화)
	 * @param blacklistNo
	 * @return
	 */
	public ServiceResult removeBlacklist(int blacklistNo);
	/**
	 * 총 갯수
	 * @param simpleCondition
	 * @return
	 */
	public int getTotalCount(PaginationInfo paging);
	
	/**
	 * 블랙리스트 다중삭제(화이트리스트화)
	 * @param blacklistNo
	 * @return
	 */
	public ServiceResult removeBlacklists(List<Integer> blacklistNo);
}
