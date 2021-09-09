package com.team2.pptor.repository;

import com.team2.pptor.domain.Article.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository < Article, Long > {

    // LIKE % 검색어 % 
    List<Article> findByTitleContaining(String title);

    // title 단건 조회
    Article findArticleByTitle (String title);

    // id 단건 조회
    Article findArticleById(Long id);

    // 페이징
    //Page<Article> findByAll(Pageable pageable);


}
