package kr.or.ddit.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Data;
@Data
public class InterviewVO implements Serializable{
	@NotBlank
	private String intvNo;
	@NotBlank
	private String applyNo;
	@NotBlank
	private LocalDateTime intvDt;
	private String intvYn;
	
	private List<EvalVO> evalVO;
}
