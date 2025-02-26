package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import kr.or.ddit.validate.DeleteGroup;
import lombok.Data;
import lombok.ToString;

@Data
public class IntroVO implements Serializable{
	@NotNull
	private Integer introNo;
	@NotBlank
	private String memId;
	@NotBlank
	private String introTitle;
	private String introDel;
	
	@ToString.Exclude
	private List<Intro_DetailVO> introDetail;
	
	@ToString.Exclude
	private MemberVO member;
}
