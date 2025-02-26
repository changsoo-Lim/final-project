package kr.or.ddit.security.authorize;

import java.util.Arrays;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

/**
 * 서비스의 로직을 대상으로 메소드 단위의 접근 제어를 하기 위한 유틸리티 메소드의 파라미터로 authentication 객체와 타입 제한
 * 없는 파라미터를 받을 수 있음.
 *
 */
@Slf4j
@Component("authCheck")
public class AuthorizeCheckUtils {
	// Authentication authentication 접근자를 알기위해서 파라미터로 받음 ex) mem_id - principal
	public <T> boolean preCheck(Authentication authentication, T...parameters) {
		log.info("user : {}, parameters in precheck : {}", authentication.getName(), Arrays.toString(parameters));
		return false;
	}

	public <T> boolean postCheck(Authentication authentication, T returnObject) {
		log.info("user : {}, returnObject in postcheck : {}", authentication.getName(), returnObject);
		return false;
	}
}