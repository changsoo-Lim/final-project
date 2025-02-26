package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.validate.UpdateGroup;
import lombok.Data;
@Data
public class ProjectVO implements Serializable{
	@NotBlank(groups = UpdateGroup.class)
	private Integer projectNo;
	
	private String compId;
	@NotBlank(groups = InsertGroup.class)
	private String projectName;
	@NotBlank(groups = InsertGroup.class)
	private String projectCont;
	@NotNull(groups = InsertGroup.class)
	private int projectSalary;
	@NotBlank(groups = InsertGroup.class)
	private String projectSkills;
	@NotBlank(groups = InsertGroup.class)
	private String projectSdt;
	@NotBlank(groups = InsertGroup.class)
	private String projectEdt;
	private String projectStatus;
	private String projectDel;
	private Long  atchFileNo;
	
	private String projectApplySdt;
	@NotBlank(groups = InsertGroup.class)
	private String projectApplyEdt;
	
	private String projectRegion;
	@NotBlank(groups = InsertGroup.class)
	private String projectWfh;
	@NotNull(groups = InsertGroup.class)
	private int projectRecruit;
	@NotBlank(groups = InsertGroup.class)
	private String projectJob;
	@NotNull(groups = InsertGroup.class)
	private int projectDeadline;
	
	private List<Free_OfferVO> freeOfferList;
	
	private int offerCnt;
	private int rnum;
	private int total;
}
