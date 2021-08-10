package com.team2.pptor.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String loginId;
    private String loginPw;
    private String name;
    private String nickname;
    private String email;
    private LocalDate regDate;
    private LocalDate updateDate;
    private boolean blind;
    private int authLevel;

    @Builder
    public Member(String loginId, String loginPw, String name, String nickname, String email) {
        this.loginId = loginId;
        this.loginPw= loginPw;
        this.name= name;
        this.nickname= nickname;
        this.email= email;
    }
}
