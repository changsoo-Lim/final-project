package kr.or.ddit.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import kr.or.ddit.vo.UsersVO;
import lombok.Data;

@Data
public class UsersVOWrapper extends User {
	public UsersVOWrapper(UsersVO realUser) {
		super(realUser.getUserId(), realUser.getUserPass(), 
				AuthorityUtils.createAuthorityList(realUser.getUserCd()));
		this.realUser = realUser;
	}
	private final UsersVO realUser;
}
