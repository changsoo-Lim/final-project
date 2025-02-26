package kr.or.ddit.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringExclude;

import lombok.Data;
@Data
public class ReviewVO implements Serializable{
	@NotNull
	private Integer reviewNo;
	@NotBlank
	private String reviewTitle;
	@NotBlank
	private String reviewCont;
	@NotNull
	private Long  reviewRating;
	private String reviewDt;
	private String reviewStatus;
	private String reviewDel;
	@NotBlank
	private String memId;
	@NotBlank
	private String compId;
	
	private Integer rnum;
	
	@ToStringExclude
	private CompanyVO companyVO;
	
	@ToStringExclude
	private CodeVO codeVO;
}
