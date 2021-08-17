package com.team2.pptor.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class Article {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id") @NotNull
    private int id;

    @ManyToOne(fetch = LAZY) // 지연로딩을 위하여 설정
    @JoinColumn(name = "member_id") // Member 와 연관관계 (주인)
    private Member member;

    @ManyToOne(fetch = LAZY) // 지연로딩을 위하여 설정
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(name = "title") @NotNull
    private String title;
    @Column(name = "body") @NotNull
    private String body;

    @Column(name = "blind") @NotNull
    private boolean blind;

    @Column(name = "reg_date") @NotNull
    private LocalDate regDate;
    @Column(name = "update_date") @NotNull
    private LocalDate updateDate;


    // 연관관계 메소드 시작 //

    public void setMember(Member member) {

        this.member = member;
        member.getArticle().add(this);

    }

    // 연관관계 메소드 끝 //

}
