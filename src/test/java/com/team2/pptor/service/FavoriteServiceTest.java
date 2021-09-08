package com.team2.pptor.service;

import com.team2.pptor.domain.Article.Article;
import com.team2.pptor.domain.Favorite.Favorite;
import com.team2.pptor.domain.Member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class FavoriteServiceTest {
    @Autowired FavoriteService favoriteService;
    @Autowired ArticleService articleService;
    @Autowired MemberService memberService;

    @Test
    void save(){
        Member member = Member.createMember("tt", "tt", "test","testnick", "test@Email.eamil", "11");
        memberService.save(member);

        Article article = Article.createArticle("test", "@S1\nhihi","<h1>hihi</h1>", member);
        articleService.save(article);

        Long articleId = 1L;

        boolean saveFavorite = favoriteService.save(member, articleId);

        assertThat(saveFavorite);
    }

    @Test
    void count(){
        Member member = Member.createMember("tt", "tt", "test","testnick", "test@Email.eamil", "11");
        memberService.save(member);

        Article article = Article.createArticle("test", "@S1\nhihi","<h1>hihi</h1>", member);
        articleService.save(article);

        Long articleId = 1L;

        boolean saveFavorite = favoriteService.save(member, articleId);

        assertThat(favoriteService.count()).isEqualTo(1L);
    }

    @Test
    void findByMember(){
        Member member = Member.createMember("tt", "tt", "test","testnick", "test@Email.eamil", "11");
        memberService.save(member);

        Article article = Article.createArticle("test", "@S1\nhihi","<h1>hihi</h1>", member);
        articleService.save(article);

        Long articleId = 1L;

        boolean saveFavorite = favoriteService.save(member, articleId);

        List<Favorite> favorites = favoriteService.findByMember(member);
        assertThat(favorites.get(0).getMember()).isEqualTo(member);
    }

    @Test
    void falseTest(){
        Member member = Member.createMember("tt", "tt", "test","testnick", "test@Email.eamil", "11");
        memberService.save(member);

        Article article = Article.createArticle("test", "@S1\nhihi","<h1>hihi</h1>", member);
        articleService.save(article);

        Long articleId = 1L;

        boolean saveFavorite = favoriteService.save(member, articleId);
        boolean saveFavorite2 = favoriteService.save(member, articleId);

        assertThat(saveFavorite2).isFalse();
    }

}
