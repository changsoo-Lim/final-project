package kr.or.ddit.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class Test_AnswerVO implements Serializable{
	@NotBlank
	private Integer answerNo;
	@NotBlank
	private Integer itemNo;
	
	private Integer candidateNo;
	
	private String answerContent;
}
