package kr.or.ddit.vo;

import java.io.Serializable;
import java.time.LocalDate;
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

@Data
public class BuyVO implements Serializable {
	@NotNull
	private Integer buyNo;
	@NotBlank
	private String productCd;
	@NotNull
	private Integer employNo;
	@NotBlank
	private String compId;
	@NotNull
	private Integer buyAmount;
	@NotBlank
	private String buyMethod;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate buyDate;
	@NotNull
	private Integer buyQty;
	@NotBlank
	private String buySdt;
	@NotBlank
	private String buyEdt;
	
	private Integer atchFileNo;

	private Integer rnum;
	
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
	
	private ProductVO prod;		// has a
	
	private EmployVO employ;	// has a
		
	private CompanyVO company;	// has a
	
	
}
