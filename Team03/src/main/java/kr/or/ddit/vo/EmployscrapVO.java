package kr.or.ddit.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
public class EmployscrapVO implements Serializable {
	private int rnum;
	private int totalCnt;
	
	private String memId;
	private Integer employNo;
	private String empscrapDel;
	
	@ToString.Exclude
	private EmployVO employ;
}	
