package kr.or.ddit.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;
import lombok.ToString;

@Data
public class CommentVO implements Serializable {
	
	@NotNull
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime commentDate;
	@NotBlank
	private Integer boardNo;
	@NotBlank
	private String memId;
	@NotBlank
	private Integer reportNo;
	@NotBlank
	private String commentCont;
	private String commentDel;
	private String formattedCommentDate;
	
	@ToString.Exclude
	private List<BoardVO> board;	 // CommentVO Has Many BoardVO (1:N)
	@ToString.Exclude
	private List<ReportVO> report;   // CommentVO Has Many ReportVO (1:N)
	@ToString.Exclude
	private List<MemberVO> member;   // CommentVO Has Many MemberVO (1:N)
}
