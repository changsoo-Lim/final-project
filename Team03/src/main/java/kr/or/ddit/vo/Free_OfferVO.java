package kr.or.ddit.vo;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.ToString;
@Data
public class Free_OfferVO implements Serializable{
	private int rnum;
	
	@NotBlank
	private String memId;
	@NotNull
	private int projectNo;
	private String freeOfferStatus;
	private LocalDate freeOfferDt;
	private String freeOfferCancelYn;
	private LocalDate freeOfferCancelDt;
	private String freeOfferDel;
	
	@ToString.Exclude
	private ProjectVO project;
	@ToString.Exclude
	private FreelancerVO freelancer;
	@ToString.Exclude
	private CodeVO code;
	
	
	private MemberVO member;
}
