package kr.or.ddit.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class Eval_CateVO implements Serializable {
	@NotNull
	private Integer evalcateNo;
	@NotNull
	private Integer evalNo;
	@NotBlank
	private String evalCateNm;
	@NotBlank
	private String evalCateCont;
	@NotNull
	private Integer evalCateScore;
	private String evalCateDel;
	
	private EvalVO evalVO;
	
	private CodeVO code;
}
