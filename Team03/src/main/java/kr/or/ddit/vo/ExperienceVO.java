package kr.or.ddit.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.ToString;

@Data
public class ExperienceVO implements Serializable {
	@NotNull
	private Integer expNo;		// 해외연수번호
	private String expCountry;  // 국가명
	private String expSdt;      // 연수 기간 시작
	private String expEdt;      // 연수 기간 종료
	private String expDesc;     // 목적 및 내용
	@NotBlank
	private String memId;		// 일반회원ID
	
	@ToString.Exclude
	private MemberVO member;
}
