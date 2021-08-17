package com.team2.pptor.repository;

import com.team2.pptor.domain.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ArticleRepository {

    private final EntityManager em;

    // 게시물 저장
    public int save(Article article){
        em.persist(article);
        return article.getId();
    }

    // 게시물 삭제
    public int delete(Article article){
        em.remove(article);
        return article.getId();
    }

//    @Transactional
//    public int modify(Article article){
//
//    }

    // 게시물 번호로 게시물 찾기
    public Article findById(int id){
        return em.find(Article.class, id);
    }

    // 게시물 리스트
    public List<Article> findAll(){
        return em.createQuery("SELECT a FROM Article a", Article.class)
                .getResultList();
    }

}
