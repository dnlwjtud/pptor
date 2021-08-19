package com.team2.pptor.service;

import com.team2.pptor.domain.Article;
import com.team2.pptor.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    public final ArticleRepository articleRepository;

    /*
    게시물 작성
     */
    @Transactional
    public void write(Article article) {
        articleRepository.save(article);
    }

    /*
    게시물 수정
     */
    @Transactional
    public void modify(){

    }

    /*
    게시물 삭제
     */
    @Transactional
    public void delete(Article article){
        articleRepository.delete(article);
    }

}
