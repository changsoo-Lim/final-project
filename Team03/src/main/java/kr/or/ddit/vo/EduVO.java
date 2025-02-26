package kr.or.ddit.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.ToString;

@Data
public class EduVO implements Serializable {
	@NotNull
	private Integer eduNo;			// 교육사항번호
	private String eduTitle;        // 과정명
	private String eduInstitution;  // 교육기관
	private String eduSdt;          // 교육기간 시작일
	private String eduEdt;          // 교육기간 종료일
	private String eduDesc;         // 수료내용
	@NotBlank
	private String memId;			// 일반회원ID
	
	@ToString.Exclude
	private MemberVO member;
}
