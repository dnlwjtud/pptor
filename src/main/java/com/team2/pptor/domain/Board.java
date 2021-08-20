package com.team2.pptor.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Board {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id") @NotNull
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL) // Article 과 연관관계(종속)
    @Column(name = "articles")
    private List<Article> articles;

    // 생성 메소드
    public static Board createBoard(String name) {

        Board board = new Board();

        board.name = name;

        return board;

    }

}
