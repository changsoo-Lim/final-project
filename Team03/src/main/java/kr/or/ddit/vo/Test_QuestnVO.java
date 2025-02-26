package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class Test_QuestnVO implements Serializable{
	@NotBlank
	private Integer queNo;
	@NotBlank
	private String queCont;
	@NotNull
	private Integer queTurn;
	@NotBlank
	private Integer testNo;
	
	private Integer atchFileNo;
	
	private String queDel;
	
    private Integer queScore; // 문제 점수
	
	private List<TestItemVO> itemList;
	
	public FileVO questionFile; 
	
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
