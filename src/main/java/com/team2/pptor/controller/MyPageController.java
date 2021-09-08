
package com.team2.pptor.controller;

import com.team2.pptor.domain.Article.Article;
import com.team2.pptor.domain.Member.Member;
import com.team2.pptor.service.ArticleService;
import com.team2.pptor.service.FollowService;
import com.team2.pptor.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final MemberService memberService;
    private final FollowService followService;
    private final ArticleService articleService;


    @GetMapping("/page/{loginId}")
    public String showMyPage(@PathVariable(name = "loginId") String loginId, Principal principal, Model model) {

        if ( !principal.getName().equals(loginId) ) {
            log.info("ERROR : 권한이 없는 시도를 하였습니다.");
            return "redirect:/";
        }

        Member findMember = memberService.findByLoginId(loginId);
        List<Article> articles = findMember.getArticles();
        int articlesCount = findMember.getArticles().size();

        model.addAttribute("member", findMember);
        model.addAttribute("articles",articles);
        model.addAttribute("articleCount", articlesCount);

        return "usr/myPage/myPage";
    }

}
