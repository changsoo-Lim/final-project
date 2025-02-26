package kr.or.ddit.paging;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 단순 키워드 검색용
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleCondition {
	private String searchType;
	private String searchWord;
	private Map<String, List<String>> searchMap;
	private String sortBy;
}
