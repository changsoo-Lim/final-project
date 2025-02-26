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
@EqualsAndHashCode(of = "noticeNo")
public class NoticeVO implements Serializable {
	private Integer noticeNo;
	@NotBlank
	private String noticeTitle;
	@NotBlank
	private String noticeCont;
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime noticeDt;
	private String noticePinned;
	
	private String noticeFaqCategory;
	
	private String noticeType;
	
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate noticePopupSdt;
	
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate noticePopupEdt;
	
	private String noticeInquiriesYn;
	
	private String noticeInquiriesCont;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDate noticeInquiriesDt;
	
	private String noticeDel;
	
	private Integer atchFileNo;
	
	private String memId;
	
	private int rowNumberDesc;
	
	private String formattedNoticeDt;
	
	private String formattedInquiriesDt;
	
	private String useAt;
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
