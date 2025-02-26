package kr.or.ddit.company.benefit.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.company.benefit.dao.BenefitMapper;
import kr.or.ddit.vo.Cmp_BftVO;
import kr.or.ddit.vo.CodeVO;

@Service
public class BenefitServiceImpl implements BenefitService {
	
	@Inject
	private BenefitMapper dao ;
	
	@Override
	public List<Cmp_BftVO> readBenefitList(String compId, String category) {
		return dao.selectBenefitList(compId, category);
	}

	@Override
	public Cmp_BftVO readBenefit(String benefitId) {
		return dao.selectBenefit(benefitId);
	}

	@Override
	public ServiceResult createBenefit(List<Cmp_BftVO> bftVO) {
	    int cnt = 0; // 성공적으로 반영된 건수를 저장할 변수
	    for (Cmp_BftVO cmp_BftVO : bftVO) {
	        // dao.insertBenefit이 실행된 행 수를 반환
	        int result = dao.insertBenefit(cmp_BftVO);
	        cnt += result; // 성공적으로 처리된 건수만 추가
	    }
	    
	    // cnt와 List 크기를 비교하여 모든 작업이 성공했는지 여부 판단
	    return cnt == bftVO.size() ? ServiceResult.OK : ServiceResult.FAIL;
	}

	@Override
	public ServiceResult modifyBenfit(Cmp_BftVO bftVO) {

		int cnt = dao.updateBenfit(bftVO); 

		return cnt > 0 ? ServiceResult.OK : ServiceResult.FAIL; 
	}

	@Override
	public ServiceResult removeBenfit(List<String> btfId) {
		
		int cnt = dao.deleteBenfit(btfId);
	
		return cnt > 0 ? ServiceResult.OK : ServiceResult.FAIL; 

	}

	@Override
	public List<CodeVO> getCode(String code) {
		return dao.getCode(code);
	}
}
