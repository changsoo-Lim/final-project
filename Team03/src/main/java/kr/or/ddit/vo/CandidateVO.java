package kr.or.ddit.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;

@Data
public class CandidateVO implements Serializable {
	@NotBlank
	private Integer candidateNo;
	@NotBlank
	private Integer testNo;
	@NotBlank
	private Integer applyNo;
	@NotBlank
	private String candidateSdt;
	@NotBlank
	private String candidateEdt;
	private Integer candidateScore;
	private String candidateYn;
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime candidateCdt;
	private Integer rnum;
}
