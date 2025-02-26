package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.validate.CompanyCreateGroup;
import kr.or.ddit.validate.CreateGroup;
import kr.or.ddit.validate.MemberCreateGroup;
import kr.or.ddit.validate.DeleteGroup;
import kr.or.ddit.validate.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "memId") // userId로 비교
public class MemberVO implements Serializable{
	@Null(groups = MemberCreateGroup.class)
	@NotBlank(groups = {UpdateGroup.class, DeleteGroup.class})
	private String memId;
	
	@Size(max = 50, groups = {MemberCreateGroup.class}) // 50자리까지
	@NotBlank(groups = {MemberCreateGroup.class})
	private String memNm;
	
	@Pattern(regexp = "^[MF]$", groups = {MemberCreateGroup.class})
	@NotBlank(groups = {MemberCreateGroup.class})
	private String memGen;
	
	@Pattern(regexp = "\\d{8}" , groups = {MemberCreateGroup.class})
	@NotBlank(groups = {MemberCreateGroup.class})
	private String memRegno;
	
	@Pattern(regexp = "\\d{5}", groups = {MemberCreateGroup.class})
	@NotBlank(groups = {MemberCreateGroup.class})
	private String memZip;
	
	@Size(max = 100, groups = {MemberCreateGroup.class})
	@NotBlank(groups = {MemberCreateGroup.class})
	private String memAddr1;
	
	@Size(max = 80, groups = {MemberCreateGroup.class})
	private String memAddr2;
	
	@Email(groups = {MemberCreateGroup.class})
	@NotBlank(groups = {MemberCreateGroup.class})
	private String memEmail;
	
	@Pattern(regexp = "\\d{11}", groups = {MemberCreateGroup.class})
	@NotBlank(groups = {MemberCreateGroup.class})
	private String memHp;
	
	private String memDateType;
	
	//@Null(groups = MemberCreateGroup.class)
	@NotBlank(groups = {MemberCreateGroup.class})
	private String memClassify;
	
	private String memStatus;
	
	private String memUrl;
	
	private String memFreelancer;
	
	private Integer  atchFileNo;
	
	
	private int rnum;
	private int total;
	
	
	private Cert_StatusVO certStatus; // has a 회원 인증정보
	
	private StatusVO status; // 알림 수신 설정
	
//	public void setMemHp(String memHp) {
//		if (StringUtils.isBlank(memHp) && memHp.length() == 11) {
//			memHp = memHp.substring(0, 3) + "-" + memHp.substring(3, 7) + "-" + memHp.substring(7);
//		}
//		this.memHp = memHp;
//	}
	@Valid
	private List<ActivityVO> activityList; // has many 봉사활동
	
	@Valid
	private List<AwardVO> awardList; // has many 수상경력
	
	@Valid
	private List<CareerVO> careerList; // has many 경력사항
	
	@Valid
	private List<CompVO> compList; // has many 컴활
	
	@Valid
	private List<CertVO> certrList; // has many 자격증
	
	@Valid
	private List<EduVO> eduList; // has many 경력사항
	
	@Valid
	private List<ExperienceVO> experienceList;	// has many 해외연수
	
	@Valid
	private List<HighSchoolVO> highSchoolList; // has many 고등학교
	
	@Valid
	private List<Lang_TestVO> langTestList; // has many 어학시험
	
	@Valid
	private List<LanguageVO> languageList; // has many 어학능력
	
	@Valid
	private List<PortfolioVO> portfolioList; // has many 포트폴리오
	
	@Valid
	private List<PrefVO> prefList; // has many 취업우대
	
	@Valid
	private List<QualificationVO> qualificationList; // has many 검정고시
	
	@Valid
	private List<UniVO> uniList; // has many 대학교
	
	@Valid
	private List<Work_CondVO> workCondList; // has many 근무조건
	
	@Valid
	private List<IntroVO> intro; // has many 자소서
	
	private BlacklistVO black;  // MemberVO Has A BlacklistVO (1:1)
	
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
