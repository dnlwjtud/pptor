package com.team2.pptor.controller;

import com.team2.pptor.domain.Article;
import com.team2.pptor.domain.Member;
import com.team2.pptor.service.ArticleService;
import com.team2.pptor.service.MemberService;
import com.team2.pptor.vo.PptorForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UsrArticleController {

    private final ArticleService articleService;

    @Autowired
    public UsrArticleController(ArticleService articleService, MemberService memberService) {
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

    /*
    PPT 수정 페이지 이동
    */
    @GetMapping("usr/article/modify")
    public String showModify(){
        return "usr/article/modify";
    }

    /*
    PPT 수정
    */
    @PostMapping("usr/article/doModify")
    public String doModify(@RequestParam("id") int id, Model model){

        model.addAttribute("modify", articleService.modify(id));

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

        model.addAttribute("detail", articleService.detail(id));

        return "usr/article/detail";
    }

    /*
    PPT 목록 페이지
    */
    //임시
    @GetMapping("usr/article/list")
    public String showList(Model model){

        model.addAttribute("list", articleService.list());

        return "usr/article/list";
    }

}