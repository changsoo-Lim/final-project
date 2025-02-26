package kr.or.ddit.company.benefit.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.vo.Cmp_BftVO;
import kr.or.ddit.vo.CodeVO;

@Mapper
public interface BenefitMapper {
	
	/**
	 * 기업 복리후생 조회
	 * @param compId (조회할 기업 아이디)
	 * @return 복리후생 리스트
	 */
	public List<Cmp_BftVO> selectBenefitList(@Param("compId") String compId,@Param("category") String category);

	/**
	 * 복리후생 상세조회
	 * @param benefitId (조회할 복리후생 아이디)
	 * @return 복리후생VO
	 */
	public Cmp_BftVO selectBenefit(String benefitId);
	
	/**
	 * 복리후생 생성
	 * @param bftVO (생성할 복리후생 내용)
	 * @return 성공 여부
	 */
	public int insertBenefit(Cmp_BftVO bftVO);
	
	/**
	 * 복리후생 업데이트
	 * @param bftVO (수정할 복리후생 내용)
	 * @return 성공 여부
	 */
	public int updateBenfit(Cmp_BftVO bftVO);
	
	/**
	 * 복리후생 삭제
	 * @param benefitId (삭제할 복리후생 아이디 , 삭제여부 Y 으로 업데이트)
	 * @return 성공 여부
	 */
	public int deleteBenfit(@Param("list") List<String> benefits);

	public List<CodeVO> getCode(String code);
	
	
	public List<Cmp_BftVO> compBftList(String compId);

	
	public List<Cmp_BftVO> selectCompBftList(String username);
	
}
