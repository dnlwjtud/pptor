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

    @Override
    public void configure(WebSecurity webSecurity) throws Exception{
        // static 디렉터리 하위 파일들은 인증없이 언제나 통과
        webSecurity.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{

        http.authorizeRequests()
                .mvcMatchers("/","/usr/member/login","usr/member/join").permitAll()
                .anyRequest().authenticated()
            .and()
                .formLogin()
                .loginPage("/usr/member/login")
                .defaultSuccessUrl("/")
                .failureUrl("/usr/member/login")
                .usernameParameter("loginId")
                .passwordParameter("loginPw")
                .loginProcessingUrl("/usr/member/doLogin")
                .successHandler(
                        new AuthenticationSuccessHandler() {
                            @Override
                            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                                log.info("INFO :: LOGON MEMBER NAME : {}", authentication.getName());
                                response.sendRedirect("/");
                            }
                        }
                )
                .failureHandler(
                        new AuthenticationFailureHandler() {
                            @Override
                            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {

                                log.info("ERROR :: {}", e.getMessage());
                                response.sendRedirect("/usr/member/login");
                            }
                        }
                )
                .permitAll()
            .and()
                .logout()
                .logoutUrl("/usr/member/logout")
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID","remember-me")
                .addLogoutHandler(new LogoutHandler() {
                    @Override
                    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
                        HttpSession session = request.getSession(false);
                        session.invalidate();
                    }
                })
                .deleteCookies("remember-me")
            .and()
                .sessionManagement()
                .invalidSessionUrl("/invalid")
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
                .expiredUrl("/expired");


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
    
}