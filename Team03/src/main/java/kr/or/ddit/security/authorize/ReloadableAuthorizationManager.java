package kr.or.ddit.security.authorize;

import java.util.List;
import java.util.function.Supplier;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.access.intercept.RequestMatcherDelegatingAuthorizationManager;
import org.springframework.security.web.access.intercept.RequestMatcherDelegatingAuthorizationManager.Builder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import kr.or.ddit.security.resource.dao.SecuredResourceMapper;
import kr.or.ddit.security.resource.vo.SecuredResourceVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ReloadableAuthorizationManager implements AuthorizationManager<HttpServletRequest>{
	
	private final SecuredResourceMapper securedResourceMapper;
	
	// RequestMatcherDelegatingAuthorizationManager처럼 위힘할 수 있는 
	private RequestMatcherDelegatingAuthorizationManager realAuthManager;
	
	@PostConstruct // 빈이 등록이 될때 무조건 실행 하기위해서 @PostConstruct로 맨마지막에 실행시킴
	public void loadSecuredResource() {
		List<SecuredResourceVO> resList = securedResourceMapper.selectResourceList();
		Builder builder = RequestMatcherDelegatingAuthorizationManager.builder();
		for (SecuredResourceVO res : resList) {
			AntPathRequestMatcher reqMatcher = new AntPathRequestMatcher(
					res.getResUrl(), res.getResMethod()
					);
			AuthorizationManager<RequestAuthorizationContext> delegateManager = null;
			if (CollectionUtils.isEmpty(res.getAuthorities())) {
				// 누구나 접근
				delegateManager = (a, c) -> new AuthorizationDecision(true); // permit all
			} else {
				// 접근 제한
				delegateManager = AuthorityAuthorizationManager
					    .hasAnyAuthority(res.getAuthorities()
					    					.stream()
					    					.toArray(String[]::new));
			}
			builder.add(reqMatcher, delegateManager);
		}
		this.realAuthManager = builder.build();
	}
	
	public void reload() {
		loadSecuredResource(); // 호출 함으로써 위에 있는 작업을 또 하게 만듬
	}

	
	@Override
	public AuthorizationDecision check(Supplier<Authentication> authentication, HttpServletRequest object) {
		return realAuthManager.check(authentication, object);
	}

}
