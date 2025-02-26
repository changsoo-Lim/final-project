package kr.or.ddit.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class VchatVO implements Serializable{
	@NotNull
	private Integer vchatNo;
	@NotNull
	private Integer filedNo;
	@NotBlank
	private String vchatTitle;
	@NotBlank
	private String vchatRid;
	@NotBlank
	private String vchatRpass;
	private String vchatUrl;
	private String vchatDt;
	private String vchatDel;
	
	private EvalVO evalVO;
	
	private FieldVO fieldVO;
}
