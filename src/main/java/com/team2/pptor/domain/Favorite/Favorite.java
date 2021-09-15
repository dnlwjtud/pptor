package com.team2.pptor.domain.Favorite;

import com.team2.pptor.domain.Article.Article;
import com.team2.pptor.domain.Board.Board;
import com.team2.pptor.domain.Member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    public Favorite(Member member, Article article){
        this.member = member;
        this.article = article;
    }
}
