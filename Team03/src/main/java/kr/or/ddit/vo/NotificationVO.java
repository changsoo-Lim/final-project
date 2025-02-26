package kr.or.ddit.vo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class NotificationVO implements Serializable{
	@NotNull
	private Integer notificNo;
	@NotBlank
	private String memId;
	@NotBlank
	private String notificUrl;
	@NotBlank
	private String notificCont;
	private LocalDateTime notificDt;
	private String notificOpenyn;
	
	private String notificType;
}	
