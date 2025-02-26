package kr.or.ddit.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class Test_KeywordVO implements Serializable{
	@NotBlank
	private Integer keywdNo;
	@NotBlank
	private String keywdCont;
	@NotNull
	private Integer keywdScore;
	@NotBlank
	private Integer itemNo;
	
	private String keywdDel;
}
