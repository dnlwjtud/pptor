package com.team2.pptor.service;

import com.team2.pptor.domain.Article.Article;
import com.team2.pptor.domain.Article.ArticleModifyForm;
import com.team2.pptor.domain.Member.Member;
import com.team2.pptor.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticleService {

    public final ArticleRepository articleRepository;

    /*
    (임시) 테스트 게시물 DB저장

    @Transactional
    public void makeTestData(Member testMember) {




        for ( int i = 1 ; i <= 10 ; i++) {

            Article article = Article.createArticle("제목" + i, "내용" + i,testMember);

            articleRepository.save(article);

        }

    }

     */

    /*
    게시물 조회
     */
    public Article findById(int id) {

        try {
            return articleRepository.findById(id);
        } catch (Exception e ) {
            throw new IllegalStateException("존재하지 않는 게시물입니다.");
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
    @Transactional
    public int modify(ArticleModifyForm articleModifyForm, Member member){

        // 게시물 번호로 게시물의 정보를 꺼냄
        Article article = articleRepository.findById(articleModifyForm.getId());

        article.modifyArticle(
                articleModifyForm.getTitle(),
                articleModifyForm.getMarkdown(),
                articleModifyForm.getHtml(),
                member
        );

        return article.getId();

    }


    /*
    게시물 삭제
     */
    @Transactional
    public void delete(int id){
        articleRepository.deleteById(id);
    }

    /*
    게시물 상세보기

    public Article detail(int id) {
        return articleRepository.findById(id);
    }
     */

    /*
    게시물 리스트
     */
    public List<Article> list() {
        return articleRepository.findAll();
    }
}
