package kr.or.ddit.resume_view.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.Resume_ViewVO;

@Mapper
public interface Resume_ViewMapper {
	
	/**
	 * 조회한 인재 기록 추가
	 * @return
	 */
	public int resumeViewInsert(Resume_ViewVO resumeViewVO); 
	
	/**
	 * 오늘기준 조회한 인재 아이디 검색
	 * @return 조회한 인재 리스트
	 */
	public List<Resume_ViewVO> resumeViewSelectList(@Param("paging") PaginationInfo<Resume_ViewVO> paging, @Param("compId") String compId);
	
	/**
	 * 인재 리스트 삭제
	 * @param memId 삭제할 인재 아이디
	 * @return 성공 여부
	 */
	public int resumeViewDelete(List<String> memId);
	
	/**
	 * 인서트 하기전 이미 값이 존재 하는지 여부 확인 
	 * @param resumeViewVO
	 * @return 
	 */
	public int resumeCheck(Resume_ViewVO resumeViewVO);
	
	/**
	 * 오늘기준 조회한 인재 아이디를 이용하여 인재 정보 검색
	 * @param memIdList
	 * @return
	 */
	public List<MemberVO> resumeViewMemberSelectList(List<String> memIdList);
	
	public int selectTotalRecorde(String compId);
}
