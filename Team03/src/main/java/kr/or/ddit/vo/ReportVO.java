package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.ToString;
@Data
public class ReportVO implements Serializable{
	@NotBlank
	private Integer reportNo;
	@NotBlank
	private String reportMemId;
	@NotBlank
	private String reportedMemId;
	@NotBlank
	private String reportReason;
	@NotBlank
	private String reportCont;
	private String reportYn;
	private String reportDt;
	
	private int boardNo;
	private int rnum;
	private String memStatus;
	
	@ToString.Exclude
	private BoardVO board;			// ReportVO Has A BoardVO (1:1)
	@ToString.Exclude
	private CommentVO comment;		// ReportVO Has A CommentVO (1:1)
	@ToString.Exclude
	private List<MemberVO> member;	// ReportVO Has Many CommentVO (1:N)
}
