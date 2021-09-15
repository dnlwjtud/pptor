package com.team2.pptor.service;

import com.team2.pptor.domain.Article.Article;
import com.team2.pptor.domain.Article.ArticleModifyForm;
import com.team2.pptor.domain.Member.Member;
import com.team2.pptor.repository.ArticleRepository;
import com.team2.pptor.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticleService {

    public final ArticleRepository articleRepository;

    /*
    테스트 게시물 생성(임시)
     */
    @Transactional
    public void makeTestData(Member testMember) {


        for ( int i = 1 ; i <= 10 ; i++) {

            // 임시
            Article testArticle = Article.createArticle(
                    "제목" + i,
                    "@S2\n# test\n## test",
                    "<p>@S2</p>\n<h1>test</h1>\n<h2>test</h2>",
                    testMember
            );

            articleRepository.saveAndFlush(testArticle);

        }

    }

    /*
    게시물 총 개수 조회
     */
    public long count() {
        return articleRepository.count();
    }

    /*
    게시물 전체 조회
     */
    public List<Article> findAll() {
        return articleRepository.findAll();
    }

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
    public void delete(Long id, @AuthenticationPrincipal CustomUserDetails user){

        Article article = articleRepository.findArticleById(id);

        if ( user.getAuthorities().toString().contains("ROLE_ADMIN") ) {
            articleRepository.delete(article);
            return;
        } else if ( user.getAuthorities().toString().equals("[ROLE_MEMBER]") )  {
            if( !article.getMember().getLoginId().equals(user.getUsername()) ){
                throw new IllegalStateException("삭제 권한이 없습니다.");
            }
        } else {
            throw new IllegalStateException("삭제 권한이 없습니다.");
        }

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
//    List<Article> findByTitleContaining(String title) {
//        return articleRepository.findByTitleContaining(title);
//    }

    // 게시물 블라인드 변경
    @Transactional
    public void modifyArticleBlind(Long articleId, CustomUserDetails user) {
        if( !user.getAuthorities().toString().contains("ROLE_ADMIN") ){
            throw new IllegalStateException("수정 권한이 없습니다.");
        }

        Article article = findById(articleId);

        if(article.isBlind()){
            article.modifyArticleBlind(false);
        }else{
            article.modifyArticleBlind(true);
        }

        articleRepository.modifyArticleBlind(article.isBlind(), article.getId());
    }

    public Page<Article> getSearchedAndPagedArticle(Pageable pageable, String searchType, String searchKeyword) {
        switch (searchType){
            case "title":
                return articleRepository.findByTitleContaining(pageable, searchKeyword);
            default:
                return articleRepository.findAll(pageable);
        }

    }
}
