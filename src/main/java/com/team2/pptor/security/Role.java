package com.team2.pptor.security;

import lombok.AllArgsConstructor;
import lombok.Getter;


// 관리자권한과 회원 권한을 설정하기 위함.
@AllArgsConstructor
@Getter
public enum Role {

    ADMIN("ROLE_ADMIN"),
    MEMBER("ROLE_MEMBER"),
    PREMEMBER("ROLE_PREMEMBER"),
    BLOCK("ROLE_BLOCK");

    private String value;

}