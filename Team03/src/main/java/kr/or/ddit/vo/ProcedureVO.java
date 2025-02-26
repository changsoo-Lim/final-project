package kr.or.ddit.vo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.validate.UpdateGroup;
import lombok.Data;
/**
 * 채용절차VO
 *
 */
@Data
public class ProcedureVO implements Serializable{
	
	private String procedureCd;
	@NotNull(groups = UpdateGroup.class)
	private Integer  filedNo;
	@NotNull(groups = InsertGroup.class)
	private int procedureTurn;
	
	// code_nm
	private String procedureCdNm;
	
	private String codeNm;
	
	private String codeCd;
}
