package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.ToString;

@Data
public class Work_CondVO implements Serializable {
	@NotBlank
	private Integer workCondNo;             //근무조건번호
	private String workCondRemote;          //재택 여부
	private String workCondJobType;         //직종
	private String workCondType;            //근무형태
	private String  workCondSalary;         //희망연봉
	private String workCondSalaryVisible;   //희망연봉공개여부(Y/N)
	@NotBlank                                              
	private String memId;					//일반회원ID
	
	@ToString.Exclude
	private MemberVO member;
	
	private String city;
	
	@ToString.Exclude
	private List<Work_CityVO> workCity;
}
