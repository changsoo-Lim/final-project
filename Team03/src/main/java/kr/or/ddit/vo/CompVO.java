package kr.or.ddit.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.ToString;

@Data
public class CompVO implements Serializable {
	@NotNull
	private Integer compSkillNo;
	private String compSkillField;
	private String compSkillDetail;
	private String compSkillLevel;
	@NotBlank
	private String memId;
	
	@ToString.Exclude
	private MemberVO member;
}
