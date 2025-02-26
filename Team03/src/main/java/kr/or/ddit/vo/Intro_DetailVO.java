package kr.or.ddit.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.ToString;
@Data
public class Intro_DetailVO implements Serializable{
	@NotNull
	private Integer introDetailNo;
	@NotNull
	private Integer introNo;
	private String introDetailTitle;
	private String introDetailCont;
	private String introDetailDel;
	
	@ToString.Exclude
	private IntroVO intro;
}
