package com.team2.pptor.repository;

import com.team2.pptor.domain.Article.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ArticleJpaRepository {

    private final EntityManager em;

    // 게시물 저장
    public Long save(Article article){
        em.persist(article);
        return article.getId();
    }

    // 게시물 삭제(Article 객체 받기)
    public Long delete(Article article){
        em.remove(article);
        return article.getId();
    }

    // 게시물 번호로 게시물 삭제
    public Long deleteById(Long id){
        Article article = findById(id);
        em.remove(article);
        return article.getId();
    }

    // 게시물 번호로 게시물 찾기
    public Article findById(Long id){
        return em.find(Article.class, id);
    }

    // 게시물 리스트
    public List<Article> findAll(){
        return em.createQuery("SELECT a FROM Article a", Article.class)
                .getResultList();
    }

}
