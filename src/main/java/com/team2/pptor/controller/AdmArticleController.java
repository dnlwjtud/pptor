package com.team2.pptor.controller;

import com.team2.pptor.domain.Article.Article;
import com.team2.pptor.domain.Member.Member;
import com.team2.pptor.security.CustomUserDetails;
import com.team2.pptor.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/adm/manage")
public class AdmArticleController {

    private final ArticleService articleService;

    /*
    게시물 관리
    */
    @GetMapping("/articles")
    public String articleManage(Model model, @AuthenticationPrincipal CustomUserDetails user,
                                @PageableDefault(size = 6, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                @RequestParam(value="searchType", defaultValue = "") String searchType,
                                @RequestParam(value="searchKeyword", defaultValue = "") String searchKeyword) {

        // ADMIN 권한이 아니면 페이지 접속 불가
        if ( !user.getAuthorities().toString().contains("ROLE_ADMIN") )  {
            return "redirect:/";
        }

        List<Article> articles = articleService.findAll();
        Page<Article> articlesPage = articleService.getSearchedAndPagedArticle(pageable, searchType, searchKeyword);

        model.addAttribute("articles", articlesPage);
        model.addAttribute("count", articleService.count());
        model.addAttribute("totalPage", articlesPage.getTotalPages());

        return "adm/article/manage";
    }

    @PutMapping("/articles/{articleId}")
    @ResponseBody
    public String blindArticle(@PathVariable(name = "articleId") Long articleId,
                              @AuthenticationPrincipal CustomUserDetails user){
        if ( !user.getAuthorities().toString().contains("ROLE_ADMIN") )  {
            return "redirect:/";
        }

        articleService.modifyArticleBlind(articleId, user);

        return "adm/article/manage";
    }

    // 관리자 게시물 삭제
    @DeleteMapping("/articles/{articleId}")
    @ResponseBody
    public String deleteArticle(@PathVariable(name = "articleId") Long articleId, @AuthenticationPrincipal CustomUserDetails user){
        if ( !user.getAuthorities().toString().contains("ROLE_ADMIN") )  {
            System.out.println("관리자가 아닙니다.");
            return "redirect:/";
        }

        articleService.delete(articleId, user);

        return "adm/article/manage";
    }

}