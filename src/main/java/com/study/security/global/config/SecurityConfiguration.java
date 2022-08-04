package com.study.security.global.config;

import com.study.security.global.exception.presentation.CustomAccessDeniedHandler;
import com.study.security.global.exception.presentation.CustomAuthenticationEntryPoint;
import com.study.security.global.jwt.JwtAuthenticationFilter;
import com.study.security.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtProvider jwtProvider;

    @Bean
    public BCryptPasswordEncoder encoderPassword() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //csrf 사용안함
        http.csrf().disable();
        //rest api로 구성한다고 가정
        http.httpBasic().disable();
        //session 사용안함
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //URL 인증여부 설정
        http.authorizeRequests()
                .antMatchers("/auth/sign-in", "/auth/sign-up").permitAll()
                .anyRequest().authenticated();

        //JwtFilter 추가
        http.addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);

        //JwtAuthentication exception handling
        //토큰 인증과정에서 발생하는 예외를 처리하기 위한 EntryPoint를 등록
        http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());

        //access Denial handler
        //인가에 실패하였을 때 예외를 발생시키는 Handler를 등록ㄴ
        http.exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler());

    }
}
