package com.team2.pptor.domain.Member;

import com.team2.pptor.domain.Article.Article;
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
    private Long id;

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

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL) // Article 과 연관관계(종속), 삭제전파 막기 위하여 cascade를 persist로 변경
    private List<Article> articles;

    @Column(name = "reg_date")
    private LocalDateTime regDate;
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "blind")
    private boolean blind;

    @Column(name = "auth_level")
    private int authLevel;
    @Column(name = "auth_key")
    private String authKey;

    // 생성 메소드
    /*
    권한 테스트 회원 생성 메소드(임시)
     */
    public static Member createTestAuthMember(String loginId, String loginPw, String name, String nickname, String email, int authLevel) {

        Member testMember = new Member();

        testMember.loginId = loginId;
        testMember.loginPw= loginPw;
        testMember.name= name;
        testMember.nickname= nickname;
        testMember.email= email;

        testMember.regDate = LocalDateTime.now();
        testMember.updateDate = LocalDateTime.now();

        testMember.blind = false;
        testMember.authLevel = authLevel;

        return testMember;
    }
    
    /*
    회원 인스턴스 생성 메소드
     */
    public static Member createMember(String loginId, String loginPw, String name, String nickname, String email, String authKey) {

        Member member1 = new Member();

        member1.loginId = loginId;
        member1.loginPw= loginPw;
        member1.name= name;
        member1.nickname= nickname;
        member1.email= email;
        member1.authKey = authKey;

        // 가입일 및 수정일을 할당
        member1.regDate = LocalDateTime.now();
        member1.updateDate = LocalDateTime.now();

        // 임시
        member1.blind = false;
        member1.authLevel = 1;  // 이메일 인증 후에는 3으로 수정하기
        member1.authKey = authKey;

        return member1;
    }

    public void changeMemberInfo( String loginPw, String nickname, String email, int authLevel ) {

        this.loginPw = loginPw;
        this.nickname = nickname;
        this.email = email;
        this.authLevel = authLevel;
    }


    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", loginId='" + loginId + '\'' +
                ", loginPw='" + loginPw + '\'' +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", articles=" + articles +
                ", regDate=" + regDate +
                ", updateDate=" + updateDate +
                ", blind=" + blind +
                ", authLevel=" + authLevel +
                ", authKey='" + authKey + '\'' +
                '}';
    }
}
