package kr.or.ddit.vo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.ToString;

@Data
public class Work_CityVO implements Serializable{
	@NotNull
	private Integer workCityNo;			 // 희망근무지역번호
	@NotNull
	private Integer workCondNo;          // 근무조건번호
	private String sidoCd;               // 시,도 코드
	private String gugunCd;              // 구,군 코드
	private String workCityYn;           // 삭제 여부
	
	@ToString.Exclude
	private Work_CondVO workCond;
	
}
