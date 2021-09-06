package com.team2.pptor.repository;

import com.team2.pptor.domain.Article.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,Long> {

    List<Article> findAll();


}
