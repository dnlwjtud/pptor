package com.team2.pptor.controller;

import com.team2.pptor.domain.Article.Article;
import com.team2.pptor.domain.Member.Member;
import com.team2.pptor.service.ArticleService;
import com.team2.pptor.domain.Article.PptorForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UsrArticleController {

    private final ArticleService articleService;

    /*
    PPT 작성 페이지 이동
     */
    @GetMapping("usr/article/write")
    public String showWrite(){
        return "usr/article/write";
    }

    /*
    PPT 작성 메소드
     */
    @PostMapping("usr/article/doWrite")
    public String doWrite(PptorForm pptorForm){

        // 임시
        Member testMember = Member.createMember(
                "user1",
                "1",
                "Member1",
                "Member1",
                "email@email.com"
        );

        Article article = Article.createArticle(
                pptorForm.getTitle(),
                pptorForm.getBody(),
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
    public String showModify(int id){

        // 파라미터 수취 확인
        System.out.println(id);

        return "usr/article/modify";
    }

    /*
    PPT 수정
    */
    @PostMapping("usr/article/doModify")
    public String doModify(@RequestParam("id") int id, Model model){

        //model.addAttribute("modify", articleService.modify(id));

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