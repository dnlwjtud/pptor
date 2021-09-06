package com.team2.pptor.security;

import jdk.jfr.Enabled;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails{

    private Long id;
    private String loginId;
    private String loginPw;
    private String name;
    private String nickname;
    private String email;
    private String authKey;
    private Collection<GrantedAuthority> AUTHORITY;

    public CustomUserDetails(Long id, String loginId, String loginPw, String name,
                             String nickname, String email, String authKey, List<GrantedAuthority> authorities) {
        this.id = id;
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.authKey = authKey;
        this.AUTHORITY = authorities;
    }


    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AUTHORITY;
    }

    @Override
    public String getPassword() {
        return loginPw;
    }

    @Override
    public String getUsername() {
        return loginId;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getAuthKey(){
        return authKey;
    }

    // 계정 만료 여부
    // true = 만료안됨, false = 만료
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠김 여부
    // true = 잠기지 않음, false = 잠김
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호 만료 여부
    // true = 만료안됨, false = 만료
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 사용자 활성화 여부
    // true = 활성, false = 비활성
    // 이메일 인증할때 인증 된 상태라면 true, 인증 안되면 false 리턴하도록 할 수 있는지 알아보기
    @Override
    public boolean isEnabled() {
        return true;
    }

}
