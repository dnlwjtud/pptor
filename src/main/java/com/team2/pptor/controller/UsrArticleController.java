package com.team2.pptor.controller;

import com.team2.pptor.domain.Article.Article;
import com.team2.pptor.domain.Article.ArticleModifyForm;
import com.team2.pptor.domain.Member.Member;
import com.team2.pptor.service.ArticleService;
import com.team2.pptor.domain.Article.ArticleSaveForm;
import com.team2.pptor.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UsrArticleController {

    private final ArticleService articleService;
    // 임시
    private final MemberService memberService;

    /*
    PPT 작성 페이지 이동
     */
    @GetMapping("usr/article/write")
    public String showWrite(Model model){

        model.addAttribute("articleSaveForm", new ArticleSaveForm());

        return "usr/article/write";
    }

    /*
    PPT 작성 메소드
     */
    @PostMapping("usr/article/doWrite")
    public String doWrite(@Validated @ModelAttribute ArticleSaveForm articleSaveForm, BindingResult bindingResult, Principal principal){

        // 오류가 확인되어 바인딩 되었다면
        if ( bindingResult.hasErrors() ) {
            // 로그에 표기와 같이 표기
            log.info("ERRORS={}",bindingResult);
            return "usr/member/join";
        }

        // Principal 객체를 이용하여 로그인한 회원의 아이디를 조회
        // 조회한 아이디로서 멤버객체를 불러오기
        Member member = memberService.findByLoginId(principal.getName());

        // 생성메소드를 통하여 게시글 객체 내부에 회원 객체 주입
        Article article = Article.createArticle(
                articleSaveForm.getTitle(),
                articleSaveForm.getBody(),
                        member
        );

        // 연관관계 메소드 호출
        article.setMember(member);

        articleService.save(article);

        return "redirect:/";

    }

    /*
    PPT 수정 페이지 이동
    */
    @GetMapping("usr/article/modify")
    public String showModify(int id, Model model){

        Article findArticle = articleService.findById(id);

        model.addAttribute("article", findArticle);
        model.addAttribute("articleModifyForm", new ArticleModifyForm());

        return "usr/article/modify";
    }

    /*
    PPT 수정
    */
    @PostMapping("usr/article/modify")
    public String doModify(@Validated @ModelAttribute ArticleModifyForm articleModifyForm, BindingResult bindingResult, @RequestParam("id") int id){

        // 수정가능여부 확인 필
        
        // 오류가 확인되어 바인딩 되었다면
        if ( bindingResult.hasErrors() ) {
            // 로그에 표기와 같이 표기
            log.info("ERRORS={}",bindingResult);
            return "usr/member/join";
        }

         articleService.modify(articleModifyForm, id);

        return "usr/article/modify";
    }

    /*
    PPT 삭제
    */
    @PostMapping("usr/article/doDelete")
    public String doDelete(@RequestParam("id") int id){

        articleService.delete(id);

        return "usr/article/list";
    }

    /*
    PPT 상세 페이지 이동
    */
    @GetMapping("usr/article/detail")
    public String showDetail(@RequestParam("id") int id, Model model){

        Article article = articleService.findById(id);

        model.addAttribute("detail", article);

        return "usr/article/detail";
    }

    /*
    PPT 목록 페이지(임시)
    */
    @GetMapping("usr/article/list")
    public String showList(Model model){

        List<Article> articles = articleService.list();

        model.addAttribute("articles", articles );
        
        return "usr/article/list";
    }

}