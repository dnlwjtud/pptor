package com.team2.pptor.domain.Article;

import com.team2.pptor.domain.Board.Board;
import com.team2.pptor.domain.Member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class Article {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private int id;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL) // 지연로딩을 위하여 설정
    @JoinColumn(name = "member_id") // Member 와 연관관계 (주인)
    private Member member;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL) // 지연로딩을 위하여 설정
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(name = "title")
    private String title;
    @Column(name = "body")
    private String body;

    @Column(name = "blind")
    private boolean blind;

    @Column(name = "reg_date")
    private LocalDateTime regDate;
    @Column(name = "update_date")
    private LocalDateTime updateDate;


    // 연관관계 메소드 시작 //

    // 회원 연동
    public void setMember(Member member) {

        this.member = member;
        member.getArticle().add(this);

        this.updateDate = LocalDateTime.now();

    }

    // 게시판 연동
    public void setBoard(Board board) {

        this.board = board;
        board.getArticles().add(this);

        this.updateDate = LocalDateTime.now();

    }

    // 연관관계 메소드 끝 //

    // 생성메소드 시작 //

    public static Article createArticle(String title, String body, Member member) {

        Article article = new Article();

        article.title = title;
        article.body = body;

        article.regDate = LocalDateTime.now();
        article.updateDate = LocalDateTime.now();

        article.member = member;

        return article;

    }

    // 생성메소드 끝 //

}
