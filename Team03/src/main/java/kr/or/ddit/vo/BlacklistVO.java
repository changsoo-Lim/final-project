package kr.or.ddit.vo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;
import lombok.ToString;

@Data
public class BlacklistVO implements Serializable {
	@NotBlank
	private int blacklistNo;
	@NotBlank
	private String blacklistReason;
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDate blacklistDt;
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDate blacklistDelDt;
	@NotBlank
	private String memId;
	
	private int reportNo;
	private int rnum;
	
	@ToString.Exclude
	private MemberVO member;  // BlacklistVO Has A MemberVO(1:1)
	@ToString.Exclude
	private List<ReportVO> report; // BlacklistVO Has Many ReportVO(1:N)
}
