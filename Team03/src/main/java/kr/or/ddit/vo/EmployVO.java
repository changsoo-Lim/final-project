package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.validate.UpdateGroup;
import lombok.Data;
import lombok.ToString;

/**
 * 채용공고VO
 *
 */
@Data
public class EmployVO implements Serializable {
	private int rnum;
	private int totalCnt;
	
	@NotNull(groups = UpdateGroup.class)
	private Integer  employNo;
	private String compId;
	@NotBlank(groups = InsertGroup.class)
	private String employTitle;
	@NotBlank(groups = InsertGroup.class)
	private String employType;
	@NotBlank(groups = InsertGroup.class)
	private String employExperience;
	@NotBlank(groups = InsertGroup.class)
	private String employEducation;
	private String employSalary;
	private String employSalaryYn;
	private String employSd;
	@NotBlank(groups = InsertGroup.class)
	private String employEd;
	@NotBlank(groups = InsertGroup.class)
	private String employWorkday;
	@NotBlank(groups = InsertGroup.class)
	private String employSwh;
	@NotBlank(groups = InsertGroup.class)
	private String employEwh;
	@NotBlank(groups = InsertGroup.class)
	private String employApplication;
	private String employUrl;
	private int employHit;
	private String employDel;
	
	private String employDday;
	private String formatStartDate;
	private String formatEndDate;
	// code_nm
	private String employTypeNm;
	private String employExperienceNm;
	private String employEducationNm;
	private String employSalaryNm;
	private String employApplicationNm;
	private String employWorkdayNm;
	private String employSwhNm;
	private String employEwhNm;
	
	private String compNm;
	private String cmpbftTitle;
	private String empscrapDel;
	private String interCompDel;
	
	private int applyCnt;
	
	@Valid
	private List<@NotNull FieldVO> fieldList;
	@ToString.Exclude
	private CodeVO code;
	@ToString.Exclude
	private CompanyVO company;
}
