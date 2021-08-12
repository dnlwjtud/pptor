package com.team2.pptor.domain;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id") @NotNull
    private int id;

    @Column(name = "login_id") @NotNull
    private String loginId;
    @Column(name = "login_pw") @NotNull
    private String loginPw;
    @Column(name = "name")
    private String name;
    @Column(name = "nickname") @NotNull
    private String nickname;
    @Column(name = "email") @NotNull
    private String email;

    @Column(name = "reg_date") @NotNull
    private LocalDate regDate;
    @Column(name = "update_date") @NotNull
    private LocalDate updateDate;

    @Column(name = "blind") @NotNull
    private boolean blind;
    @Column(name = "auth_level") @NotNull
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
