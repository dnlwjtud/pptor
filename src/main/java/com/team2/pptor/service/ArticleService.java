package com.team2.pptor.service;

import com.team2.pptor.domain.Article.Article;
import com.team2.pptor.domain.Article.ArticleModifyForm;
import com.team2.pptor.domain.Member.Member;
import com.team2.pptor.repository.ArticleJpaRepository;
import com.team2.pptor.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticleService {

    // public final ArticleJpaRepository articleRepository;
    private final ArticleRepository articleRepository;

    /*
    게시물 조회
     */
    public Article findById(Long id) {

        Optional<Article> findArticle = articleRepository.findById(id);

        return findArticle.orElseThrow(() -> new NoSuchElementException("해당 게시물은 존재하지 않습니다"));

        /*
        try {
            return articleRepository.findArticleById(id);
        } catch (Exception e ) {
            throw new IllegalStateException("존재하지 않는 게시물입니다.");
        }
        */

    }

    /*
    게시물 작성
     */
    @Transactional
    public Article save(Article article) {
        return articleRepository.save(article);
    }

    /*
    게시물 수정
    */
    @Transactional
    public void modify(ArticleModifyForm articleModifyForm, Member member){

        Article findArticle = articleRepository.findArticleById(articleModifyForm.getId());

        if ( findArticle != null ) {
            findArticle.modifyArticle(
                    articleModifyForm.getTitle(),
                    articleModifyForm.getMarkdown(),
                    articleModifyForm.getHtml(),
                    member
            );
        } else {
            throw new NoSuchElementException("해당 게시물은 존재하지 않습니다.");
        }


        /*
        상황에 맞는 Optional 사용이 어려움

        // 게시물 번호로 게시물의 정보를 꺼냄
        //Article article = articleRepository.findById(articleModifyForm.getId());

        Optional<Article> articleOptional = articleRepository.findById(articleModifyForm.getId());

        articleOptional.ifPresent(
                article -> {
                    article.modifyArticle(
                            articleModifyForm.getTitle(),
                            articleModifyForm.getMarkdown(),
                            articleModifyForm.getHtml(),
                            member
                    );
                }
                
        );
         */

    }

    /*
    게시물 번호로 삭제
     */
    @Transactional
    public void delete(Long id){
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

    /*
    제목으로 게시물 검색
     */
    List<Article> findByTitleContaining(String title) {
        return articleRepository.findByTitleContaining(title);
    }

}
