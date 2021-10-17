package com.blockChain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.CorsFilter;

import com.blockChain.jwt.JwtAccessDeniedHandler;
import com.blockChain.jwt.JwtAuthenticationEntryPoint;
import com.blockChain.jwt.TokenProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터(이 클래스)가 스프링 필터체인에 등록됨
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	private final TokenProvider tokenProvider;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	private final CorsFilter corsFilter;
	@Override//configure치고 ctrl+space 누르니까 해당 메소드가 자동완성됨 개꿀
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable() //csrf비활성화
			.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
			.accessDeniedHandler(jwtAccessDeniedHandler)
			.and().cors()
			// h2-console 을 위한 설정을 추가//
			.and().headers().frameOptions().sameOrigin()
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
//			.addFilter(corsFilter)
			.formLogin().disable()
			
			.httpBasic().disable()
			.authorizeRequests()
			.antMatchers("/user/**").authenticated()
			//.antMatchers("/Admin/**").hasRole("ADMIN")//어드민만 들어갈 수잇다.
			.anyRequest().permitAll()
			.and().apply(new JwtSecurityConfig(tokenProvider));//위에 걸어둔거 말고는 누구든 들어올 수 있다.
			//로그인한 상태를 요구하는 페이지일경우 자동으로 로그인창으로 이동
//		super.configure(http);
	}
}
