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
    public Article modify(int id){

    }

    /*
    게시물 삭제
     */
    @Transactional
    public void delete(int id){
        articleRepository.delete(id);
    }

    /*
    게시물 상세보기
     */
    public Object detail(int id) {
        articleRepository.findById(id);
    }

    /*
    게시물 리스트
     */
    public Object list() {
        articleRepository.findAll();
    }
}
