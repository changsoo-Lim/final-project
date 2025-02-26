package kr.or.ddit.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.validate.UpdateGroup;
import lombok.Data;
import lombok.ToString;

@Data
public class ActivityVO implements Serializable {
	@NotBlank
	private Integer activityNo;				// 대외활동번호
	private String activityOrganization;    // 기관명
	private String activityDesc;            // 활동내용
	private String activitySdt;             // 활동기간 시작일
	private String activityEdt;             // 활동기간 종료일
	@NotBlank
	private String memId;					// 일반회원ID
	
	@ToString.Exclude
	private MemberVO member;
}


