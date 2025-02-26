package kr.or.ddit.company.benefit.service;

import java.util.List;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.vo.Cmp_BftVO;
import kr.or.ddit.vo.CodeVO;

public interface BenefitService {
	/**
	 * 기업 복리후생 조회
	 * @param compId (조회할 기업 아이디)
	 * @return 복리후생 리스트
	 */
	public List<Cmp_BftVO> readBenefitList(String compId, String category);

	/**
	 * 복리후생 상세조회
	 * @param benefitId (조회할 복리후생 아이디)
	 * @return 복리후생VO
	 */
	public Cmp_BftVO readBenefit(String benefitId);
	
	/**
	 * 복리후생 생성
	 * @param bftVO (생성할 복리후생 내용)
	 * @return 성공 여부
	 */
	public ServiceResult createBenefit(List<Cmp_BftVO> listBftVO);
	
	/**
	 * 복리후생 업데이트
	 * @param bftVO (수정할 복리후생 내용)
	 * @return 성공 여부
	 */
	public ServiceResult modifyBenfit(Cmp_BftVO bftVO);
	
	/**
	 * 복리후@Override
	생 삭제
	 * @param benefitId (삭제할 복리후생 아이디 , 삭제여부 N 으로 업데이트)
	 * @return 성공 여부
	 */
	public ServiceResult removeBenfit(List<String> btfId);

	public List<CodeVO> getCode(String code);

}
