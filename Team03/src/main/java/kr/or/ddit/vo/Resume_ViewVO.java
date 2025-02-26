package kr.or.ddit.vo;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import lombok.Data;
@Data
public class Resume_ViewVO implements Serializable{
	@NotBlank
	private int resumeViewNo;
	@NotBlank
	private String compId;
	@NotBlank
	private String memId;
	private LocalDate resDt;
	private String resDel;
}
