package kr.or.ddit.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.ToString;
@Data
public class PortfolioVO implements Serializable{
	@NotBlank
	private Integer portNo;
	private String portNm;
	private String portUrl;
	@NotBlank
	private String memId;
	
	@ToString.Exclude
	private MemberVO member;
}
