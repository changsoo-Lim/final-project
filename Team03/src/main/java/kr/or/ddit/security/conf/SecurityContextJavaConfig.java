package kr.or.ddit.security.conf;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import kr.or.ddit.security.authenticate.CustomAuthenticationSuccessHandler;
import kr.or.ddit.security.authorize.ReloadableAuthorizationManager;
import kr.or.ddit.security.resource.dao.SecuredResourceMapper;

@Configuration
@EnableWebSecurity
public class SecurityContextJavaConfig {
	
	// AuthenticationManager 주입
	@Autowired
	private AuthenticationManagerBuilder authenticationManagerBuilder;
	
	// 비밀번호 등 Credentials 자원 삭제 
	@PostConstruct
	public void init() {
		authenticationManagerBuilder.eraseCredentials(true);
	}
	
	// 비밀번호 암호화 및 평문비밀번호와 DB 암호회된 비밀번호 비교
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	// 웹 보호 자원 설정 해제
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers("/resources/**");
	}
	
	// SecuredResource Mapper 주입
	@Inject
	private SecuredResourceMapper resMapper;
	
	// 접근 권한 결정하는 메소드 호출
	@Bean
	public AuthorizationManager<HttpServletRequest> customAuthrozationManager() {
		return new ReloadableAuthorizationManager(resMapper);
	}
	// 접근 설정 / 로그인 / 로그아웃 설정
	@Bean 
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.anonymous().authorities("ROLE_ANONYMOUS")
			.and()
			.addFilterAt(new AuthorizationFilter(customAuthrozationManager()), AuthorizationFilter.class) // 접근 권한 필터링 
			.formLogin()
				.loginPage("/login") // 사용자 정의 로그인 페이지 경로
				.loginProcessingUrl("/performLogin") // 로그인 처리 URLs
				.successHandler(customAuthenticationSuccessHandler())
				.failureUrl("/login?error=true") // 로그인 실패 시 이동할 URL
				.usernameParameter("userId") // 사용자 ID 필드명
				.passwordParameter("userPass") // 비밀번호 필드명
			.and()
			.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login") // 로그아웃 성공 후 이동할 URL
				.invalidateHttpSession(true) // 세션 무효화
			.and()
		    .sessionManagement()
		    	.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
			.and()
			.csrf().disable(); // csrf 보호 해제

		return http.build();
	}
	// 특정 url 권한 확인
	@Bean
	public HandlerMappingIntrospector handlerIntrospector() {
		return new HandlerMappingIntrospector();
	}
	// 로그인 성공 시 처리
	@Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler(); // 로그인 성공 후 처리 핸들러 빈 등록
    }
}
