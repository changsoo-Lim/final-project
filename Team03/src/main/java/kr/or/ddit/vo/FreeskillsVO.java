package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.ToString;

@Data
public class FreeskillsVO implements Serializable {
	@NotBlank
	private int freeskillsNo;
	
	@NotBlank
	private String memId;
	@NotBlank
	private String freeskillsType;
	@NotBlank
	private String freeskillsProf;
	
	@ToString.Exclude
	private String freeskillsTypeNm;
	@ToString.Exclude
	private String freeskillsProfNm;
	
	@ToString.Exclude
	private List<CodeVO> codeList;
}
