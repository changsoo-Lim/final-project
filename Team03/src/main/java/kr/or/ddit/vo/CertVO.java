package kr.or.ddit.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.ToString;

@Data
public class CertVO implements Serializable {
	
	private String certCode;	// 눈에만 보이는 자격증 이름

	@NotNull
	private Integer certNo;		// 자격증번호             
	private String certNm;      // 자격증이름    
	private String certIssuer;  // 자격증발행처   
	private String certDate;    // 자격증취득일자  
	private String certPassCd;  // 자격증합격구분코드
	@NotBlank
	private String memId;		// 일반회원ID
	
	@ToString.Exclude
	private MemberVO member;
}
