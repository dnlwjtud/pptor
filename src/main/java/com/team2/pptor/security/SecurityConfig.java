package com.team2.pptor.security;

import com.team2.pptor.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // antMatchers 대신 mvcMatchers로 변경했습니다.
    // 로그아웃 하지 않고 서버종료 후 재가동시 로그인 할 때 /invalid 페이지로 이동합니다, 로그인은 됨.
    // 그 후 로그아웃하고 다시 로그인 할때는 invalid로 이동하지 않습니다.

    @Autowired
    private UserDetailsService userDetailsService;
    private MemberService memberService;

    @Override
    public void configure(WebSecurity webSecurity) throws Exception{
        // static 디렉터리 하위 파일들은 인증없이 언제나 통과
        webSecurity.ignoring().mvcMatchers("/css/**", "/js/**", "/img/**", "/error/**" ,"/lib/**");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .authorizeRequests(authorize -> authorize
                                .mvcMatchers(
                                        "/members/login"
                                        , "/members/join"
                                        ,"/members/findPw").anonymous()
                                .mvcMatchers(
                                        "/"
                                        , "/articles/list"
                                        , "boards/list"
                                        , "/articles/detail"
                                        , "/send/mail/**"
                                        , "/make/test/data"
                                        , "/sample/**"
                                        , "/page/**"
                                ).permitAll()
                                .mvcMatchers(
                                        "/adm/**"
                                        , "/adm/boards/**").hasRole("ADMIN") // ADMIN 권한을 가진 계정만 접근가능
                                .mvcMatchers(  // MEMBER 권한을 가진 계정만 접근가능
                                        "/members/myPage"
                                        , "/members/findPw"
                                        , "/members/**"
                                        , "/articles/write"
                                        , "/articles/view"
                                        , "/articles/**"
                                        , "/api/**"
                                ).hasRole("MEMBER")
                                // 인증지정한,인가없이 접근 가능.
                                .anyRequest()  //  antMatchers로  페이지 이외의 다른모든 페이지(antMatchers로 지정하고 permitAll로 접근 허용을 지정 한 뒤에 써주기)
                                .denyAll()
                        // .authenticated() // 인증이 된 사용자만 접근할 수 있도록 제한
                )// 로그인 설정 시작
                .formLogin()  // form을 통해 로그인 활성
                .loginPage("/members/login")  // 로그인 페이지 접근할 때 띄워줄 페이지, 지정하지 않으면 Spring Security에서 제공하는 기본 폼이 나온다. loginPage(지정주소)의 지정주소가 controller에서 @GetMapping으로 받는 주소가일치해야 한다.
                .loginProcessingUrl("/doLogin")  // 로그인 처리 URL 설정
                .usernameParameter("loginId")  // from에서 보내는 loginId를 받을 파라미터 key값
                .passwordParameter("loginPw")  // loginPw를 받을 파라미터 key값, 둘다 input의 name과 일치하도록.
                .defaultSuccessUrl("/") // 로그인 성공시 이동할 페이지
                .and() //로그아웃 설정 시작
                .logout()  // 로그아웃 관련 설정 진행을 돕는 LogoutConfigurer<> 클래스를 반환.
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout")) // 로그아웃 주소 지정(따로 getMapping 할 필요는 없다)
                .logoutSuccessUrl("/") // 로그아웃 성공 후 이동페이지
                .invalidateHttpSession(true) // 로그아웃 시 인증정보 지우기, 세션 무효화
                .deleteCookies("JSESSIONID") // 쿠키 제거
                .clearAuthentication(true) // 권한 정보 제거
                .and() // 동시 세션 제어, 세션 고정 보호, 세션 정책 시작
                .sessionManagement() // 세션 관리 기능 작동
                .invalidSessionUrl("/") // 세션 유효하지 않을 때 이동될 URL // 임시주석
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true) // 동시 로그인 차단
                .expiredUrl("/"); // 세션 만료시 이동될 URL



    }

    @Bean
    public PasswordEncoder passwordEncoder(){  // 비밀번호 암호화 객체
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }
    // spring security의 인증은 AuthenticationManager를 통해 이루어진다.
    // AuthenticationManager를 생성하기 위해 AuthenticationManagerBuilder 사용.
    // 인증을 위해서 UserDetailsService를 통해 필요한 정보를 가져온다.
    // MemberService에서 UserDetailsService 인터페이스를 implements해서 loadUserByUsername() 구현.


    // 로그아웃 할 때 invalidateHttpSession(true)가 정상작동 하지 않는다.
    // 아래 메서드를 추가해서 Bean 등록할 것.
    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
    }


}