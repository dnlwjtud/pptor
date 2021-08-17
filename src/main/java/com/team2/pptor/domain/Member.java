package com.team2.pptor.domain;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL) // Article 과 연관관계(종속)
    private List<Article> article;

    @Column(name = "reg_date") @NotNull
    private LocalDateTime regDate;
    @Column(name = "update_date") @NotNull
    private LocalDateTime updateDate;

    @Column(name = "blind") @NotNull
    private boolean blind;
    @Column(name = "auth_level") @NotNull
    private int authLevel;

    // 빌더패턴
    @Builder
    public Member(String loginId, String loginPw, String name, String nickname, String email) {

        this.loginId = loginId;
        this.loginPw= loginPw;
        this.name= name;
        this.nickname= nickname;
        this.email= email;

        // 가입일 및 수정일을 할당
        regDate = LocalDateTime.now();
        updateDate = LocalDateTime.now();

    }






}
