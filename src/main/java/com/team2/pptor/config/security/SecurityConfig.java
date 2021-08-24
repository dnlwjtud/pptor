package com.team2.pptor.config.security;

import com.team2.pptor.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    private MemberService memberService;

    /*
    웹 필수 로드 파일 검사 통과
     */
    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        // static 디렉터리 하위 파일들은 인증없이 언제나 통과
        webSecurity.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
    }

    /*
    시큐리티 설정
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                .mvcMatchers("/admin/**").hasRole("ADMIN") // ADMIN 권한을 가진 계정만 접근가능
                .mvcMatchers(  // MEMBER 권한을 가진 계정만 접근가능
                        "/usr/member/myPage"
                        , "/usr/member/modify"
                        , "/usr/article/write"
                        , "/usr/article/doWrite"
                        , "/usr/article/modify"
                        , "/usr/article/doModify"
                        , "/usr/article/doDelete").hasRole("MEMBER")
                .mvcMatchers(
                        "/usr/member/login"
                        , "/usr/member/join").anonymous()
                .mvcMatchers(
                        "/"
                        , "/usr/article/list"
                        , "/usr/article/detail"
                        , "/make/test/data"
                ).permitAll()  // 인증,인가없이 접근 가능.
                .anyRequest()  //  antMatchers로 지정한 페이지 이외의 다른모든 페이지(antMatchers로 지정하고 permitAll로 접근 허용을 지정 한 뒤에 써주기)
                .authenticated() // 인증이 된 사용자만 접근할 수 있도록 제한
            .and()// 로그인 설정 시작
                .formLogin()  // form을 통해 로그인 활성
                .loginPage("/usr/member/login")  // 로그인 페이지 접근할 때 띄워줄 페이지, 지정하지 않으면 Spring Security에서 제공하는 기본 폼이 나온다. loginPage(지정주소)의 지정주소가 controller에서 @GetMapping으로 받는 주소가일치해야 한다.
                .loginProcessingUrl("/doLogin")  // 로그인 처리 URL 설정
                .usernameParameter("loginId")  // from에서 보내는 loginId를 받을 파라미터 key값
                .passwordParameter("loginPw")  // loginPw를 받을 파라미터 key값, 둘다 input의 name과 일치하도록.
                .defaultSuccessUrl("/") // 로그인 성공시 이동할 페이지
            .and() //로그아웃 설정 시작
                .logout()  // 로그아웃 관련 설정 진행을 돕는 LogoutConfigurer<> 클래스를 반환.
                .logoutRequestMatcher(new AntPathRequestMatcher("/usr/member/logout")) // 로그아웃 주소 지정(따로 getMapping 할 필요는 없다)
                .logoutSuccessUrl("/") // 로그아웃 성공 후 이동페이지
                .invalidateHttpSession(true) // 로그아웃 시 인증정보 지우기, 세션 무효화
                .deleteCookies("JSESSIONID") // 쿠키 제거
                .clearAuthentication(true) // 권한 정보 제거
            .and() // 동시 세션 제어, 세션 고정 보호, 세션 정책 시작
                .sessionManagement() // 세션 관리 기능 작동
                .invalidSessionUrl("/invalid") // 세션 유효하지 않을 때 이동될 URL
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true) // 동시 로그인 차단
                .expiredUrl("/expired"); // 세션 만료시 이동될 URL

    }

    /*
    패스워드 인코더
     */
    @Bean
    public PasswordEncoder passwordEncoder(){  // 비밀번호 암호화 객체
        return new BCryptPasswordEncoder();
    }

    /*
    패스워드 인코더
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }
    
}