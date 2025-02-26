package kr.or.ddit.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class Cmp_BftVO implements Serializable {
	@NotBlank
	private String cmpbftNo;
	@NotBlank
	private String compId;
	@NotBlank
	private String cmpbftTile;

	private String cmpbftDel;
	
	private String codeNm;
}
