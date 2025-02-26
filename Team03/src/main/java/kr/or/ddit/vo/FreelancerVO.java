package kr.or.ddit.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
@Data
@EqualsAndHashCode(of = "memId")
public class FreelancerVO implements Serializable{
	private int rnum;
	
	@NotBlank
	private String memId;
	private String memNm;
	
	@NotBlank
	private String freeField;
	@NotBlank
	private String freeJob;
	@NotBlank
	private String freeStyle;
	@NotNull
	private int freeSalary;
	@NotBlank
	private String freeSalarytype;
	@NotBlank
	private String freeType;
	@NotBlank
	private String freeSdt;
	@NotBlank
	private String freeExperience;
	@NotNull
	private int freeCareer;
	@NotBlank
	@ToString.Exclude
	private String freeDetail;
	private Integer atchFileNo;
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDateTime freeUpdate;
	
	@JsonIgnore
	@ToString.Exclude
	@Nullable
	@Valid
	private FileVO atchFile;
	
	@JsonIgnore
	@ToString.Exclude
	@Nullable
	@Valid
	private FileVO memAtchFile;
	
	@JsonIgnore
	@ToString.Exclude
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
			atchFile = new FileVO();
			atchFile.setFileDetails(fileDetails);
		}
	}
	@ToString.Exclude
	private CodeVO code;
	@ToString.Exclude
	private int memImageAtchFileNo;
	@ToString.Exclude
	private List<FreeskillsVO> freeskills;
	@ToString.Exclude
	private String freeskillsTypeList;
	@ToString.Exclude
	private String freeskillsProfList;
}
