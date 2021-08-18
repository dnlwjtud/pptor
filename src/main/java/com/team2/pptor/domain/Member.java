package com.team2.pptor.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private int id;

    @Column(name = "login_id")
    private String loginId;
    @Column(name = "login_pw")
    private String loginPw;
    @Column(name = "name")
    private String name;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL) // Article 과 연관관계(종속)
    private List<Article> article;

    @Column(name = "reg_date")
    private LocalDateTime regDate;
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "blind")
    private boolean blind;
    @Column(name = "auth_level")
    private int authLevel;

    // 생성 메소드
    
    /*
    회원 인스턴스 생성 메소드
     */
    public static Member createMember(String loginId, String loginPw, String name, String nickname, String email) {

        Member member1 = new Member();

        member1.loginId = loginId;
        member1.loginPw= loginPw;
        member1.name= name;
        member1.nickname= nickname;
        member1.email= email;

        // 가입일 및 수정일을 할당
        member1.regDate = LocalDateTime.now();
        member1.updateDate = LocalDateTime.now();

        // 임시
        member1.blind = false;
        member1.authLevel = 3;

        return member1;
    }

    public void changeMemberInfo( String loginPw, String nickname, String email ) {

        this.loginPw = loginPw;
        this.nickname = nickname;
        this.email = email;

    }






}
