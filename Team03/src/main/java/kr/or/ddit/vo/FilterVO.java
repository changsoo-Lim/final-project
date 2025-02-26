package kr.or.ddit.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.validate.UpdateGroup;
import lombok.Data;
/**
 * 서류자격요건VO
 *
 */
@Data
public class FilterVO implements Serializable{
	
	private Integer  filterNo;
	@NotNull(groups = UpdateGroup.class)
	private Integer  filedNo;
	@NotBlank(groups = InsertGroup.class)
	private String filterTitleCd;
	@NotBlank(groups = InsertGroup.class)
	private String filterContCd;
	
	// code_nm
	private String filterTitleCdNm;
	private String filterContCdNm;
}
