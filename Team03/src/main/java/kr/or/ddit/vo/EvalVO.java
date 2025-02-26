package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class EvalVO implements Serializable {
	@NotNull
	private Integer evalNo;
	@NotNull
	private Integer vchatNo;
	private String evalDt;
	private String evalDel;
	@NotNull
	private Integer intvNo;
	
	private List<Eval_CateVO> evalCateVO;
}
