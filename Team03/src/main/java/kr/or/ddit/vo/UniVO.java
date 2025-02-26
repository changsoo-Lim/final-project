package kr.or.ddit.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.ToString;

@Data
public class UniVO implements Serializable{
	@NotNull
	private Integer uniNo;					// 대학번호
	private String uniNm;    			// 대학교명
	private String uniType;    			// 대학 (2,3년,4년, 대학원)
	private String uniSdt;    			// 대학 재학기간 시작일자
	private String uniSStatus;    		// 대학 입학여부
	private String uniEdt;    			// 대학 재학기간 종료일자
	private String uniEStatus;    		// 대학 종료여부
	private String uniMajorCategory;    // 대학 전공 (계열)
	private String uniMajorNm;    		// 대학 전공명
	private String uniMajorDegree;    	// 대학 석박사
	private Double uniGpa;    			// 대학 학점
	private String uniProjectNm;    	// 대학 수업 및 프로젝트 명
	private String uniProjectDesc;    	// 대학 수업 내용
	@NotBlank                              
	private String memId;				// 일반 회원 ID
	
	@ToString.Exclude
	private MemberVO member;
}
