package com.team2.pptor.controller;

import com.team2.pptor.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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


}
