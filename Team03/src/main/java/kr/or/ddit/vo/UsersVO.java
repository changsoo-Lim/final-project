package kr.or.ddit.vo;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.validation.annotation.Validated;

import kr.or.ddit.validate.CompanyCreateGroup;
import kr.or.ddit.validate.MemberCreateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
@Data
@EqualsAndHashCode(of = "userId") // userId로 비교
@Validated
public class UsersVO implements Serializable{
//	@Pattern(regexp = "[a-zA-Z\\d]{4,20}") // \\w = A-Za-z 정규식 
	@NotBlank(groups = {CompanyCreateGroup.class, MemberCreateGroup.class})
	private String userId;
	
	@NotBlank(groups = {CompanyCreateGroup.class, MemberCreateGroup.class})
	 // 소문자 포함, 숫자 포함, 특수문자( !@#$%^&*() ) 포함, ( A-Za-z\\d!@#$%^&*() )만 사용 할 수 있고, 8자리부터 20자리 사이의 값체크
//	@Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()])[A-Za-z\\d!@#$%^&*()]{8,20}$") 
	@ToString.Exclude			// toString 출력 제외
	private String userPass;
	
//	@Pattern(regexp = "/^\\d{8}$/") // 8자리의 숫자 입력
	private String userJoindt;
	
//	@Pattern(regexp = "/^USER0\\d$/") // USER0? 가 입력되어야함
	@NotBlank(groups = {CompanyCreateGroup.class, MemberCreateGroup.class})
	private String userCd;
	
	private int total;
	
	@Valid
	private MemberVO memberVO;     // has a 관계
	
	@Valid
	private CompanyVO companyVO; 	 // has a 관계
	
	@Valid
	private UserrollVO userRoleVO; // has a 관계
	
	@Valid
	private StatusVO statusVO;  	 // has a 관계
	
	private Cert_StatusVO certStatusVO; 		// has a 관계
	
	
	public UsersVO() {
        this.memberVO = new MemberVO();
        this.companyVO = new CompanyVO();
        this.statusVO = new StatusVO();
        this.userRoleVO = new UserrollVO();
        this.certStatusVO = new Cert_StatusVO();
    }
}
