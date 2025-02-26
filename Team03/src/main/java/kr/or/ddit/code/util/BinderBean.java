package kr.or.ddit.code.util;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import kr.or.ddit.code.service.CodeService;
import kr.or.ddit.vo.CodeVO;

/**
 * BinderBean 클래스는 ApplicationContext에서 CodeService Bean을 가져와
 * 코드 정보를 제공하는 유틸리티 클래스입니다.
 * 
 * CodeService를 통해 코드를 조회하고, 관련된 유틸리티 메서드를 제공합니다.
 */
@Component
public class BinderBean implements ApplicationContextAware {

    private static CodeService codeService;
    
    /**
     * 특정 부모 코드(CODE_PARENT)를 기준으로 하위 코드 리스트를 반환합니다.
     *
     * @param codeParent 조회할 부모 코드 값
     * @return 하위 코드 리스트
     */
    public static List<CodeVO> getCodeList(String codeParent) {
        if (codeParent == null || codeService == null) {
            return null;
        }
        return codeService.selectByParent(codeParent);
    }

    /**
     * 부모 코드(CODE_PARENT)가 특정 패턴과 유사한 하위 코드 리스트를 반환합니다.
     *
     * @param codeParent 조회할 유사한 부모 코드 값
     * @return 하위 코드 리스트
     */
    public static List<CodeVO> getCodeListLike(String codeParent) {
        if (codeParent == null || codeService == null) {
            return null;
        }
        return codeService.selectByParentLike(codeParent);
    }

    /**
     * 부모 코드(CODE_PARENT)가 특정 목록 내에 포함된 하위 코드 리스트를 반환합니다.
     *
     * @param codeParent 조회할 부모 코드 목록
     * @return 하위 코드 리스트
     */
    public static List<CodeVO> getCodeListIn(String codeParent) {
        if (codeParent == null || codeService == null) {
            return null;
        }
        return codeService.selectByParentIn(codeParent);
    }

    /**
     * 부모 코드(CODE_PARENT)의 하위 코드를 서브쿼리로 조회하여 반환합니다.
     *
     * @param codeParent 서브쿼리에 사용할 부모 코드 값
     * @return 하위 코드 리스트
     */
    public static List<CodeVO> getCodeListInSubquery(String codeParent) {
        if (codeParent == null || codeService == null) {
            return null;
        }
        return codeService.selectByParentInSubquery(codeParent);
    }
    
    /**
     * 특정 코드(CODE_CD)를 기준으로 단일 코드를 반환합니다.
     *
     * @param code 조회할 코드 값
     * @return CodeVO 객체 (해당 코드 정보)
     */
    public static CodeVO getCode(String code) {
    	if (codeService == null || code == null) {
    		return null;
    	}
    	return codeService.selectCodeByCode(code);
    }
    /**
     * 코드 값(CODE_CD)이 특정 패턴과 유사한 코드 리스트를 반환합니다.
     *
     * @param codeCd 유사한 코드 값
     * @return 코드 리스트
     */
    public static List<CodeVO> getCodeListByCodeLike(String codeCd) {
    	if (codeCd == null || codeService == null) {
    		return null;
    	}
    	return codeService.selectByCodeCdLike(codeCd);
    }

    /**
     * Spring ApplicationContext를 설정하고 CodeService Bean을 주입합니다.
     *
     * @param context ApplicationContext 객체
     * @throws BeansException ApplicationContext 설정 중 오류 발생 시 예외
     */
    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        codeService = context.getBean(CodeService.class);
    }
}
