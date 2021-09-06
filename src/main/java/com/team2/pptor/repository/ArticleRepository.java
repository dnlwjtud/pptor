package com.team2.pptor.repository;

import com.team2.pptor.domain.Article.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository < Article, Long > {

    /*
    @Query("select a from Article a where a.title = :title")
    List<Article> findArticlesByTitle ( @Param("title") String title );
     */

    // LIKE % 검색어 % 
    List<Article> findByTitleContaining(String title);

    // title 단건 조회
    Article findArticleByTitle (String title);

    // id 단건 조히
    Article findArticleById(Long id);


}
