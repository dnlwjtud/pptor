package com.team2.pptor.controller;

import com.team2.pptor.domain.Article.Article;
import com.team2.pptor.security.CustomUserDetails;
import com.team2.pptor.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdmArticleController {

    private final ArticleService articleService;

    /*
    게시물 관리
    */
    @GetMapping("adm/article/manage")
    public String articleManage(Model model, @AuthenticationPrincipal CustomUserDetails user) {

        // admin 권한이 아니면 페이지 접속 불가
        if ( !user.getAuthorities().toString().equals("[ROLE_ADMIN]") )  {
            return "redirect:/";
        }

        List<Article> articles = articleService.findAllArticles();

        model.addAttribute("articles", articles);
        model.addAttribute("count", articleService.count());

        return "adm/article/manage";
    }

}