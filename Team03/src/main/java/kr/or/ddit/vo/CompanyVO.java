package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

import org.apache.commons.lang3.builder.ToStringExclude;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.or.ddit.validate.CompanyCreateGroup;
import kr.or.ddit.validate.CompanyInfoUpdateGroup;
import kr.or.ddit.validate.CreateGroup;
import kr.or.ddit.validate.DeleteGroup;
import kr.or.ddit.validate.UpdateGroup;
import lombok.Data;

@Data
public class CompanyVO implements Serializable {
	@Null(groups = { CompanyCreateGroup.class })
	@NotBlank(groups = { UpdateGroup.class, DeleteGroup.class })
	private String compId;

	@NotBlank(groups = { CompanyCreateGroup.class })
	private String compNum;

	@NotBlank(groups = { CompanyCreateGroup.class })
	private String compNm;

	@NotBlank(groups = { CompanyCreateGroup.class })
	private String compRepresentative;

	@NotBlank(groups = { CompanyCreateGroup.class })
	private String compType;

	@NotBlank(groups = { CompanyCreateGroup.class })
	private String compInd;

	@NotBlank(groups = { CompanyCreateGroup.class })
	private String compJob;

	@NotBlank(groups = { CompanyCreateGroup.class })
	private String compJobDetail;

	@NotBlank(groups = { CompanyCreateGroup.class })
	private String compZip;

	@NotBlank(groups = { CompanyCreateGroup.class })
	private String compAddr1;

	private String compAddr2;

	@NotBlank(groups = { CompanyCreateGroup.class, CompanyInfoUpdateGroup.class })
	private String compPhone;

	@NotBlank(groups = { CompanyCreateGroup.class, CompanyInfoUpdateGroup.class })
	private String compEmail;

	@NotBlank(groups = { CompanyCreateGroup.class, CompanyInfoUpdateGroup.class })
	private String compMobile;

	private String compCont;

	private String compStatus;

	private Integer atchFileNo;

	private int RNUM;

	@ToStringExclude
	private List<Cmp_BftVO> bftList;

	@ToStringExclude
	private List<ReviewVO> reviewList;

	@ToStringExclude
	private List<BuyVO> buyList;
	
	private List<EmployVO> employList;

	
	@JsonIgnore
//	@ToString.Exclude
	@Nullable
	@Valid
	private FileVO file;

	@JsonIgnore
//	@ToString.Exclude
	private MultipartFile[] uploadFiles;

	public void setUploadFiles(MultipartFile[] uploadFiles) {
		List<File_DetailVO> fileDetails = Optional.ofNullable(uploadFiles).map(Arrays::stream).orElse(Stream.empty())
				.filter(f -> !f.isEmpty()).map(File_DetailVO::new).collect(Collectors.toList());
		if (!fileDetails.isEmpty()) {
			this.uploadFiles = uploadFiles;
			file = new FileVO();
			file.setFileDetails(fileDetails);
		}
	}
}
