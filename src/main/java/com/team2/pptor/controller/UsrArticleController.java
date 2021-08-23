package com.team2.pptor.controller;

import com.team2.pptor.domain.Article.Article;
import com.team2.pptor.domain.Article.ArticleModifyForm;
import com.team2.pptor.domain.Member.Member;
import com.team2.pptor.service.ArticleService;
import com.team2.pptor.domain.Article.ArticleSaveForm;
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

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UsrArticleController {

    private final ArticleService articleService;

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
    public String doWrite(@Validated @ModelAttribute ArticleSaveForm articleSaveForm, BindingResult bindingResult){

        // 오류가 확인되어 바인딩 되었다면
        if ( bindingResult.hasErrors() ) {
            // 로그에 표기와 같이 표기
            log.info("ERRORS={}",bindingResult);
            return "usr/member/join";
        }

        // 임시
        Member testMember = Member.createMember(
                "user1",
                "1",
                "Member1",
                "Member1",
                "email@email.com"
        );

        Article article = Article.createArticle(
                articleSaveForm.getTitle(),
                articleSaveForm.getBody(),
                        testMember
        );

        article.setMember(testMember);

        articleService.save(article);

        return "redirect:/";
    }

    /*
    PPT 수정 페이지 이동
    */
    @GetMapping("usr/article/modify")
    public String showModify(int id, Model model){

        // 파라미터 수취 확인
        System.out.println(id);

        model.addAttribute("articleModifyForm", new ArticleModifyForm());

        return "usr/article/modify";
    }

    /*
    PPT 수정
    */
    @PostMapping("usr/article/modify")
    public String doModify(@Validated @ModelAttribute @RequestParam("id") int id, Model model, BindingResult bindingResult){

        //model.addAttribute("modify", articleService.modify(id));

        // 오류가 확인되어 바인딩 되었다면
        if ( bindingResult.hasErrors() ) {
            // 로그에 표기와 같이 표기
            log.info("ERRORS={}",bindingResult);
            return "usr/member/join";
        }

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

        Article article = articleService.detail(id);

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