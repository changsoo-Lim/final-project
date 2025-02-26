package kr.or.ddit.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.builder.ToStringExclude;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class CodeVO implements Serializable {
	@NotBlank
	private String codeCd;
	@NotBlank
	private String codeNm;
	private String codeParent;
}	
