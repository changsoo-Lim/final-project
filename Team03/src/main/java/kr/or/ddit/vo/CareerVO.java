package kr.or.ddit.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.ToString;

@Data
public class CareerVO implements Serializable {
	@NotNull
	private Integer careerNo;                // 경력사항번호
	private String careerCompanyNm;         // 회사명
	private String careerIndustryType;      // 업종
	private String careerSubIndustry;       // 상세업종
	private String careerCompanySize;       // 기업규모
	private String careerCompanyType;       // 기업형태
	private String careerListed;            // 상장여부
	private String careerCity;              // 시,도
	private String careerDistrict;          // 구,군
	private String careerStartDate;         // 근무기간 시작일
	private String careerEndDate;           // 근무기간 종료일
	private String careerTentre;			// 재직여부
	private String careerJobTitle;          // 담당업무
	private String careerJobDesc;           // 업무 내용
	private String careerPosition;          // 직급
	private String careerDepartment;        // 근무부서
	private String careerType;              // 근무 형태
	private Integer careerSalary;              // 연봉
	private String careerReason;            // 퇴사사유
	@NotBlank                                 
	private String memId;					// 일반회원ID
	
	@ToString.Exclude
	private MemberVO member;
}
