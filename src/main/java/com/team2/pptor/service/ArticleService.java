package com.team2.pptor.service;

import com.team2.pptor.domain.Article;
import com.team2.pptor.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    public final ArticleRepository articleRepository;

    /*
    (임시) 테스트 게시물 DB저장
     */
    @Transactional
    public void makeTestData() {

        for ( int i = 0 ; i < 10 ; i++) {

            Article article = Article.createArticle("제목" + i, "내용" + i);

            articleRepository.save(article);

        }

    }

    /*
    게시물 작성
     */
    @Transactional
    public void save(Article article) {
        articleRepository.save(article);
    }

    /*
    게시물 수정
     */
    //@Transactional
    //public Article modify(int id){
//
//   }

    /*
    게시물 삭제
     */
    @Transactional
    public void delete(int id){
        articleRepository.deleteById(id);
    }

    /*
    게시물 상세보기
     */
    public Article detail(int id) {
        return articleRepository.findById(id);
    }

    /*
    게시물 리스트
     */
    public List<Article> list() {
        return articleRepository.findAll();
    }
}
