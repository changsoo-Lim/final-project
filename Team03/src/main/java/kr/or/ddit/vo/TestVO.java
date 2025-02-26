package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.ToString;
@Data
public class TestVO implements Serializable{
	@NotBlank
	private Integer testNo;
	@NotBlank
	private String  compId;
	@NotBlank
	private String  testCd;
	@NotBlank
	private String  testNm;
	private Integer testTime;
	private String  testDel;
	
	private String codeNm;
	
	@ToString.Exclude
	private Integer rnum;
	
	private CodeVO code;
	
	private List<Test_QuestnVO> testQuestnList; // 문제 리스트
}
