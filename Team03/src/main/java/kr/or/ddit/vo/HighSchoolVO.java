package kr.or.ddit.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.ToString;
@Data
public class HighSchoolVO implements Serializable{
	@NotNull
	private Integer highschoolNo;
	private String highschoolNm;
	private String highschoolLocation;
	private String highschoolPeriod;
	private String highschoolStatus;
	private String highschoolMajor;
	@NotBlank
	private String memId;
	
	@ToString.Exclude
	private MemberVO member;
}
