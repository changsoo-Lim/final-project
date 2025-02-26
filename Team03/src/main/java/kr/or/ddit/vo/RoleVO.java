package kr.or.ddit.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;
@Data
public class RoleVO implements Serializable{
	@NotBlank
	private String roleCd;
	@NotBlank
	private String roleNm;
}
