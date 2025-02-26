package kr.or.ddit.resume_view.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.Resume_ViewVO;

public interface Resume_ViewService {
	
	/**
	 * 조회한 인재 기록 추가 
	 * @return 성공 여부
	 */
	public ServiceResult resume_ViewInser(Resume_ViewVO resumeViewVO);
	 
	/**
	 * 오늘기준 조회한 인재 아이디 검색
	 * @return 조회한 인재 리스트
	 */
	public List<MemberVO> resumeViewSelectList(@Param("paging") PaginationInfo<Resume_ViewVO> paging, @Param("compId") String compId);
	
	/**
	 * 인재 리스트 삭제
	 * @param memId 삭제할 인재 아이디
	 * @return 성공 여부
	 */
	public ServiceResult resumeViewDelete(List<String> memId);
	
}
