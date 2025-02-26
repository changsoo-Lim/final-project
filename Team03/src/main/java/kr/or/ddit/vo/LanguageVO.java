package kr.or.ddit.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.ToString;
@Data
public class LanguageVO implements Serializable{
	@NotBlank
	private Integer langNo;				// 어학능력번호
	private String langNm;              // 외국어명
	private String langSpeakingLevel;   // 회화수준
	private String langReadingLevel;    // 독해수준
	private String langWritingLevel;    // 작문수준
	@NotBlank
	private String memId;				// 일반회원ID
	
	@ToString.Exclude
	private MemberVO member;
}
