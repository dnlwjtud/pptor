package com.team2.pptor.repository;

import com.team2.pptor.domain.Article.Article;
import com.team2.pptor.domain.Member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@Transactional
@SpringBootTest
class ArticleRepositoryTest {

    @Autowired  ArticleRepository articleRepository;

    /*
    저장 테스트 (통과)
     */
    @Test
    public void saveTest() {

        
        Article article = new Article();
        Article savedArticle = articleRepository.save(article);

        // findById -> Optional 리턴
        Article findArticle = articleRepository.findById(savedArticle.getId()).get();

        assertThat(findArticle.getId()).isEqualTo(findArticle.getId());
        assertThat(findArticle).isEqualTo(article);

    }

    /*
    CRUD 테스트 (통과)
     */
    @Test
    public void CRUDTest() {

        Article article1 = Article.createArticle("제목1","내용1","11", new Member());
        Article article2 = Article.createArticle("제목2","내용2","22", new Member());

        articleRepository.save(article1);
        articleRepository.save(article2);

        Article findArticle1 = articleRepository.findById(article1.getId()).get();
        Article findArticle2 = articleRepository.findById(article2.getId()).get();

        // 단건조회 테스트
        assertThat(findArticle1).isEqualTo(article1);
        assertThat(findArticle2).isEqualTo(article2);

        // 전체 조회 테스트
        List<Article> findAllArticles = articleRepository.findAll();
        assertThat(findAllArticles.size()).isEqualTo(2);

        // 전체 게시물 개수 조회 테스트
        long articlesCount = articleRepository.count();
        assertThat(articlesCount).isEqualTo(2);

        // 삭제 검증
        articleRepository.delete(article1);
        articleRepository.delete(article2);

        long deletedArticlesCount = articleRepository.count();

        assertThat(deletedArticlesCount).isEqualTo(0);

    }

    /*
    제목으로 게시물 리스트 뽑아오기
     */
    @Test
    public void findByTitleContainingTest() {
        
        // 테스트 데이터 생성
        Article article1 = Article.createArticle("제목1","내용1","11", new Member());
        Article article2 = Article.createArticle("제목2","내용2","22", new Member());

        // 게시물 리스트 뽑아오기
        // 생성된 데이터 저장
        articleRepository.save(article1);
        articleRepository.save(article2);
        
        // 검색
        //List<Article> findArticles = articleRepository.findByTitleContaining("제목");

        // 결과도출
        // System.out.println(findArticles.toString());
        /*
        검색된 게시물 제목 : 제목1
        검색된 게시물 제목 : 제목2
         */
//        for (Article findArticle : findArticles) {
//            System.out.println("검색된 게시물 제목 : " + findArticle.getTitle());
//        }

    }

    @Test
    public void findArticleByTitle() {

        // 테스트 데이터 생성
        Article article1 = Article.createArticle("제목1","내용1","11", new Member());

        // 테스트 데이터 저장
        articleRepository.save(article1);

        // title로 게시글 찾아오기
        String title = "제목1";

        Article findArticle = articleRepository.findArticleByTitle(title);

        // 검증
        assertThat(findArticle).isEqualTo(article1);

    }

    @Test
    public void findSingleArticleById() {

        // 테스트 데이터 생성
        Article article1 = Article.createArticle("제목1","내용1","11", new Member());

        // 테스트 데이터 저장
        Article savedArticle = articleRepository.save(article1);

        // id로 게시글 찾아오기
        Article findArticle = articleRepository.findArticleById(savedArticle.getId());

        // 검증
        assertThat(findArticle).isEqualTo(savedArticle);

    }
    
    /*
    게시글 닉네임으로 페이징 테스트
     */
    @Test
    public void memberPagingTest() {

        // 테스트용 회원 생성
        Member member = Member.createMember(
                "user1",
                "1",
                "회원" ,
                "회원1" ,
                "test@test.com",
                "11"
        );

        // 테스트용 게시물 생성
        for ( int j = 0 ; j <= 50 ; j++ ) {
            Article article = Article.createArticle(
                    "제목",
                    "12",
                    "12",
                    member
            );
            // 게시물 저장
            articleRepository.save(article);
        }

        // 저장 확인
        List<Article> articles = articleRepository.findAll();
        System.out.println(articles);

    }

}