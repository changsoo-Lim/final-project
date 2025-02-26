package kr.or.ddit.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;
@Data
public class MenuVO implements Serializable{
	@NotBlank
	private String menuNo;
	private String menuCd;
	@NotBlank
	private String menuNm;
	private String menuShow;
	@NotBlank
	private String roleCd;
}
