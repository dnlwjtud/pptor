package com.team2.pptor.domain.Board;

import com.sun.istack.NotNull;
import com.team2.pptor.domain.Article.Article;
import com.team2.pptor.domain.Member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Board {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id") @NotNull
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "reg_date")
    private LocalDateTime regDate;
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL) // Article 과 연관관계(종속)
    @Column(name = "articles")
    private List<Article> articles;


    // 게시판 수정 메소드
    public void modifyBoard(String name){

        this.name = name;

        this.updateDate = LocalDateTime.now();

    }

    // 연관관계 메소드
    public void setArticles(Article article) {

        articles.add(article);

    }

    // 생성 메소드
    public static Board createBoard(String name) {

        Board board = new Board();

        board.name = name;

        board.regDate = LocalDateTime.now();
        board.updateDate = LocalDateTime.now();

        return board;

    }

}
