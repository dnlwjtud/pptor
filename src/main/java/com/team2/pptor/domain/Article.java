package com.team2.pptor.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Article {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id") @NotNull
    private int id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
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


}
