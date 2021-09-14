package com.team2.pptor.domain.Article;

import com.team2.pptor.domain.Board.Board;
import com.team2.pptor.domain.Member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class Article {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    @ManyToOne(fetch = LAZY) // 지연로딩을 위하여 설정 , 삭제 전파를 막기 위하여 cascade를 Persist로 변경 , cascade = CascadeType.PERSIST
    @JoinColumn(name = "member_id") // Member 와 연관관계 (주인)
    private Member member;

    @ManyToOne(fetch = LAZY) // 지연로딩을 위하여 설정  , cascade = CascadeType.ALL
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(name = "title")
    private String title;

    @Column(name = "markdown", columnDefinition = "LONGTEXT")
    private String markdown; // 마크다운 내용

    @Column(name = "html", columnDefinition = "LONGTEXT")
    private String html; // html 내용

    @Column(name = "blind", columnDefinition = "boolean default false")
    private boolean blind;

    @Column(name = "reg_date")
    private LocalDateTime regDate;
    @Column(name = "update_date")
    private LocalDateTime updateDate;


    // 연관관계 메소드 시작 //

    // 회원 연동
    public void setMember(Member member) {

        this.member = member;
        member.getArticles().add(this);

    }

    // 게시판 연동
    public void setBoard(Board board) {

        this.board = board;
        board.getArticles().add(this);

    }
    
    // 게시물 수정 메소드
    public void modifyArticle(String title, String markdown, String html, Member member){

        this.title = title;
        this.markdown = markdown;
        this.html = html;

        this.updateDate = LocalDateTime.now();

        setMember(member);

    }

    // 게시물 블라인드 수정 메소드
    public void modifyArticleBlind(boolean blind){

        this.blind = blind;

        this.updateDate = LocalDateTime.now();

    }


    // 연관관계 메소드 끝 //

    // 생성메소드 시작 //

    public static Article createArticle(String title, String markdown, String html, Member member) {

        Article article = new Article();

        article.title = title;
        article.markdown = markdown;
        article.html = html;

        article.regDate = LocalDateTime.now();
        article.updateDate = LocalDateTime.now();

        article.member = member;

        return article;

    }

    // 생성메소드 끝 //
    

}
