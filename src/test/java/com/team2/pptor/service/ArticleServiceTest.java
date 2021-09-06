package com.team2.pptor.service;

import com.team2.pptor.domain.Article.Article;
import com.team2.pptor.domain.Member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ArticleServiceTest {

    @Autowired ArticleService articleService;

    @Test
    public void findByIdTest() {

        // 테스트 데이터 생성
        Article article1 = Article.createArticle("제목1","내용1","11", new Member());

        // 테스트 데이터 저장
        Article savedArticle = articleService.save(article1);

        Article findArticle = articleService.findById(savedArticle.getId());

        // 검증
        assertThat(savedArticle).isEqualTo(article1);

        // 검증2
        assertThat(findArticle).isEqualTo(savedArticle);

        // 검증3
        try {
            articleService.findById(3L);
        } catch (Exception e ) {
            System.out.println("게시물을 찾을 수 없습니다.");
        }

    }


}