package kr.or.ddit.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.ToString;
@Data
public class Lang_TestVO implements Serializable{
	@NotBlank
	private Integer langTestNo;		// 어학시험번호
	private String langTestName;    // 어학시험명
	private String langTestLevel;   // 점수 및 등급
	private String langTestDate;    // 취득일자
	@NotBlank
	private String memId;			// 일반회원ID
	
	@ToString.Exclude
	private MemberVO member;
}
