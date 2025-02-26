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

@Data
public class ApplyVO implements Serializable {

	@NotNull
    private Integer applyNo; // 공고 지원자 번호

    @NotBlank
    private String memId; // 일반 회원 ID

    @NotNull
    private Integer filedNo; // 모집 분야 번호

    private String appProcStatus; // 지원 절차 상태

    @DateTimeFormat(iso = ISO.DATE_TIME)
    private LocalDateTime appProcChangeDt; // 지원 절차 상태 변경 일시

    @DateTimeFormat(iso = ISO.DATE_TIME)
    private LocalDateTime appDate; // 지원 일시

    private String appOpenYn; // 지원서 열람 여부

    private String appCancelYn; // 지원 취소 여부

    @DateTimeFormat(iso = ISO.DATE_TIME)
    private LocalDateTime appCancelDt; // 지원 취소 일시

    private String appDel; // 지원 내역 삭제 여부

    private Integer atchFileNo; // 이력서 파일 그룹 번호
    
    private String appPassYn; // 합격 여부
    private Integer rnum;
    private String compNm;
    private String employTitle;
    private String filedNm;
    private String formattedAppDate; // 포맷된 지원 일시
    private Integer employNo;
    
    // 연관 객체
    private CodeVO code; // 공통 코드 정보 (지원 절차 상태)
    private MemberVO member; // 회원 정보
    private List<FieldVO> field; // 모집 분야 정보
    private EmployVO employ; // 채용 공고 정보 (FIELD와 연결된 상위 관계)
    private List<ProcedureVO> procedure; // 절차 정보
    private StatusVO status;
    private List<InterviewVO> interviews;
    private List<TestVO> test;
    private List<CandidateVO> candidate;
    private CompanyVO company;
    
    private String codeNm;
    
//  @JsonIgnore
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