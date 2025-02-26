package kr.or.ddit.code.constants;

public class CacheConstants {
    public static final String CODE_BY_CODE = "codeByCode"; // 단일 코드 조회 캐시
    public static final String CODES_BY_PARENT = "codesByParent"; // 부모 코드 기반 하위 코드 리스트 캐시
    public static final String CODES_BY_PARENT_LIKE = "codesByParentLike"; // 유사한 부모 코드 기반 하위 코드 리스트 캐시
    public static final String CODES_BY_PARENT_IN = "codesByParentIn"; // 여러 부모 코드 기반 하위 코드 리스트 캐시
    public static final String CODES_BY_PARENT_SUBQUERY = "codesByParentSubquery"; // 서브쿼리로 조회된 하위 코드 리스트 캐시
    public static final String CODES_BY_CODE_LIKE = "codesByCodeLike"; // 유사한 코드 값 기반 코드 리스트 캐시
}