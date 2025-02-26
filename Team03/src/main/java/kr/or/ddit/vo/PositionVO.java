package kr.or.ddit.vo;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.ibatis.annotations.Insert;

import kr.or.ddit.validate.InsertGroup;
import lombok.Data;
@Data
public class PositionVO implements Serializable{
	private int rnum;
	private int totalCnt;
	
	@NotBlank(groups = InsertGroup.class)
	private String memId;
	@NotNull(groups = InsertGroup.class)
	private Integer filedNo;
	@NotBlank
	private String compId;
	private LocalDate positionDate;
	private String positionStatusCd;
	private String positionCancelYn;
	private LocalDate positionCancelDt;
	private String positionDel;
	
	private CompanyVO company;
	
	private EmployVO employ;
	
	private FieldVO field;
	
	private MemberVO member;
	
	private int total;
	
}
