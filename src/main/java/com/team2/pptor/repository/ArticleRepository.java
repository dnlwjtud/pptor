package com.team2.pptor.repository;

import com.team2.pptor.domain.Article.Article;
import com.team2.pptor.domain.Member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository < Article, Long > {

    // LIKE % 검색어 % 
//    List<Article> findByTitleContaining(String title);

    // title 단건 조회
    Article findArticleByTitle (String title);

    // id 단건 조회
    Article findArticleById(Long id);

    // 블라인드 수정
    @Modifying
    @Query(value="update Article a set a.blind = :blind WHERE a.id = :id")
    void modifyArticleBlind(boolean blind, Long id);

    // 페이징
    Page<Article> findAll(Pageable pageable);

    Page<Article> findByTitleContaining(Pageable pageable, String SearchKeyword);

}
