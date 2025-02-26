package kr.or.ddit.vo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;

@Data
public class FileVO implements Serializable{
	private Integer atchFileNo;
	private String creatDt;
	private boolean useAt;
	
	
	

//	@ToString.Exclude
	@Nullable
	@Valid
	private List<@NotNull File_DetailVO> fileDetails; // FileVO Has Many File_DetailVO (1:N)
}
