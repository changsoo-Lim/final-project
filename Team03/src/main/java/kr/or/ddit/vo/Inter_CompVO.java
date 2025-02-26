package kr.or.ddit.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.ToString;
@Data
public class Inter_CompVO implements Serializable{
	@NotBlank
	private String memId;
	@NotBlank
	private String compId;
	private String interCompDt;
	private String interCompDel;
	
	private int rnum;
	private int total;
	
	@ToString.Exclude
	private EmployVO employ;
	
	private MemberVO member;
	
}
