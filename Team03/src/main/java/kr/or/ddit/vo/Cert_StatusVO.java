package kr.or.ddit.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

import kr.or.ddit.validate.MemberCreateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "memId") // userId로 비교
public class Cert_StatusVO implements Serializable {
	@Null(groups = MemberCreateGroup.class)
	@NotBlank
	private String memId;	// 회원아이디
	
	private String emailYn;	// 이메일 인증여부
	private String hpYn;	// 휴대폰번호 인증여부
}


