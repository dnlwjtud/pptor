package com.team2.pptor.repository;

import com.team2.pptor.domain.Article.Article;
import com.team2.pptor.domain.Member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired ArticleRepository articleRepository;

    @Test
    public void pageTest() {

        


    }
    
    @Test
    public void getArticlesTest() {

        // 게시글 작성할 멤버 생성
        Member member1 = Member.createMember(
                "user1",
                "1",
                "회원1",
                "user1",
                "test@test.com",
                "11"
        );

        Member savedMember = memberRepository.save(member1);

        // 멤버가 게시글 작성
        Article article = Article.createArticle(
                "제목1",
                "1234",
                "1234",
                member1
        );

        // 게시글 저장
        articleRepository.save(article);

        // 회원이 작성한 게시물 리스트 받아오기
        //List<Article> articles = memberRepository.findArticlesByLoginId(savedMember.getLoginId());

        // 검증
        //Assertions.assertThat(articles.size()).isEqualTo(1);

    }

    @Test
    public void findByLoginIdTest() {

        // 테스트 회원 생성
        Member member1 = Member.createMember(
                "user1",
                "1",
                "회원1",
                "user1",
                "test@test.com",
                "11"
        );

        // 회원 저장
        Member savedMember = memberRepository.save(member1);

        // 회원 불러오기, 테스트를 위하여 바로 get
        Member findMember = memberRepository.findByLoginId(savedMember.getLoginId()).get();

        // 검증
        assertThat(savedMember).isEqualTo(findMember);
        assertThat(savedMember.getLoginId()).isEqualTo(findMember.getLoginId());

    }


}