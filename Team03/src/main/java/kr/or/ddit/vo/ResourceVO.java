package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "ResourceNo")
public class ResourceVO implements Serializable{
	@NotBlank
	private Integer resourceNo;
	@NotBlank
	private String resourceTitle;
	private String resourceDel;
	private Integer  atchFileNo;
	
	private int rownum;
	private String resourceType;
	private Integer fileDwncnt;
	
	
	@JsonIgnore
	@Nullable
	@Valid
	private FileVO file;
	
	private File_DetailVO fileDetails; // 기존 파일 VO와의 연관 관계
	
	@JsonIgnore
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
