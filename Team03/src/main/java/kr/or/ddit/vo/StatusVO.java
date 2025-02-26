package kr.or.ddit.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

import kr.or.ddit.validate.CreateGroup;
import kr.or.ddit.validate.MemberCreateGroup;
import kr.or.ddit.validate.DeleteGroup;
import kr.or.ddit.validate.UpdateGroup;
import lombok.Data;
@Data
public class StatusVO implements Serializable{
	@Null(groups = MemberCreateGroup.class)
	@NotBlank(groups = {UpdateGroup.class})
	private String memId;
	@NotBlank(groups = {UpdateGroup.class})
	private String statusSms;
	@NotBlank(groups = {UpdateGroup.class})
	private String statusEmail;
	private String statusRec;
	private String statusPosition;
	private String statusComment;
	private String statusResume;
}
