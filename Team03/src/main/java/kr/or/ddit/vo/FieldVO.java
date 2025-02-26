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
 * 모집분야VO
 *
 */
@Data
public class FieldVO implements Serializable {
	private Integer filedNo;
	@NotNull(groups = UpdateGroup.class)
	private Integer employNo;
	@NotBlank(groups = InsertGroup.class)
	private String filedNm;
	@NotBlank(groups = InsertGroup.class)
	private String filedRegion;
	private String filedRegionGungu;
	private int filedPersonnel;
	private String filedJobs;
	private String filedPreference;
	private String filedEndYn;
	
	private int applyCount;
	
	// code_nm
	private String filedRegionNm;
	private String filedRegionGunguNm;
	
	@Valid
	private List<@NotNull ProcedureVO> procedure;
	@Valid
	private List<@NotNull FilterVO> filterList;
	@ToString.Exclude
	private CodeVO code;
	
	private List<ApplyVO> applyList;
	
	private EmployVO employ; 
	
}
