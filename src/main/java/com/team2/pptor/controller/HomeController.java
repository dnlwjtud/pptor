package com.team2.pptor.controller;

import com.team2.pptor.repository.BoardRepository;
import com.team2.pptor.service.ArticleService;
import com.team2.pptor.service.BoardService;
import com.team2.pptor.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ArticleService articleService;
    private final MemberService memberService;
    private final BoardService boardService;

    @GetMapping("/")
    public String home(){
        return "index";
    }

    /*
    프론트 체크용 테스트 데이터 주입(임시)
     */
    @GetMapping("/make/test/data")
    public String makeTestData() {

        // 회원 테스트
        // 비밀번호 인코딩이 안되기 때문에 로그인 불가능
        //memberService.makeTestData();

        // 게시판 테스트
        boardService.makeTestData();

        // 게시글 테스트
        articleService.makeTestData();

        return "redirect:/";
    }


}
