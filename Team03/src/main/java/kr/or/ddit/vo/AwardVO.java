package kr.or.ddit.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.ToString;

@Data
public class AwardVO implements Serializable {
	@NotNull
	private Integer awardNo;			// 수상경력번호
	@NotBlank
	private String awardTitle;		// 수상내역
	private String awardIssuer;     // 수여기관
	private String awardDate;       // 수상날짜
	@NotBlank
	private String memId;			// 일반회원ID
	
	@ToString.Exclude
	private MemberVO member;
}
