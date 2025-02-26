package kr.or.ddit.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.ToString;
@Data
public class QualificationVO implements Serializable{
	@NotBlank
	private Integer qualificationNo;
	private String qualificationDt;
	@NotBlank
	private String memId;
	
	@ToString.Exclude
	private MemberVO member;
}
