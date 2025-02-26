package kr.or.ddit.vo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = "BoardNo")
public class BoardVO implements Serializable {
	@NotBlank
	private Integer boardNo;
	@NotBlank
	private String memId;
	@NotBlank
	private Integer reportNo;
	@NotBlank
	private String boardTitle;
	private String boardCont;
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDateTime boardDt;
	private String boardDel;
	private Integer atchFileNo;
	
	private String timeDifference;  // 시간 차이 저장용 필드
	private Integer rnum;
	
	@ToString.Exclude
	private CommentVO comment; 		// BoardVO Has A CommentVO (1:1)
	@ToString.Exclude
	private List<MemberVO> member;  // BoardVO Has Many MemberVO (1:N)
	@ToString.Exclude
	private List<ReportVO> report;  // BoardVO Has Many ReportVO (1:N)
	
	
	@JsonIgnore
//	@ToString.Exclude
	@Nullable
	@Valid
	private FileVO file;
	
	@JsonIgnore
//	@ToString.Exclude
	private MultipartFile[] uploadFiles;
	public void setUploadFiles(MultipartFile[] uploadFiles) {
		List<File_DetailVO> fileDetails = Optional.ofNullable(uploadFiles)
													.map(Arrays::stream)
													.orElse(Stream.empty())
													.filter(f->!f.isEmpty())	
													.map(File_DetailVO::new)
													.collect(Collectors.toList());
		if(!fileDetails.isEmpty()) {
			this.uploadFiles = uploadFiles;
			file = new FileVO();
			file.setFileDetails(fileDetails);
		}
	}
	

}
