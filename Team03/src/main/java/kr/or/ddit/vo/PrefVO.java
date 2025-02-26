package kr.or.ddit.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.ToString;
@Data
public class PrefVO implements Serializable{
	@NotBlank
	private Integer prefNo;              // 취업우대번호
	private String prefMilitary;        // 병역사항
	private String prefPatriot;         // 보훈대상
	private String prefDisability;      // 장애여부
	private String prefEmploySupport;   // 고용지원금 대상
	private String prefHobbies;         // 취미, 관심
	private String prefSkills;          // 특기
	@NotBlank                           
	private String memId;				// 일반회원ID
	
	@ToString.Exclude
	private MemberVO member;
}
