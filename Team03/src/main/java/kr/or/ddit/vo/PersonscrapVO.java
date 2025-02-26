package kr.or.ddit.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;
@Data
public class PersonscrapVO implements Serializable{
	@NotBlank
	private String compId;
	@NotBlank
	private String memId;
	private String perscrapDt;
	private String perscrapDel;
}
