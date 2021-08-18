package com.team2.pptor.controller;

import com.team2.pptor.domain.Article;
import com.team2.pptor.service.ArticleService;
import com.team2.pptor.vo.PptorForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsrArticleController {

    private final ArticleService articleService;

    @Autowired
    public UsrArticleController(ArticleService articleService) {

        this.articleService = articleService;

    }

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

        /*
        // 확인용 메서드
        System.out.println(pptorForm.getBody());
        System.out.println(pptorForm.getTitle());
         */

        Article article = Article.createArticle(
                pptorForm.getTitle(),
                pptorForm.getBody()
        );

        articleService.write(article);

        return "redirect:/";
    }



}
