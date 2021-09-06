package com.team2.pptor.controller;

import com.team2.pptor.domain.Member.Member;
import com.team2.pptor.mail.MailService;
import com.team2.pptor.repository.BoardRepository;
import com.team2.pptor.security.CustomUserDetails;
import com.team2.pptor.service.ArticleService;
import com.team2.pptor.service.BoardService;
import com.team2.pptor.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
    샘플페이지 이동
     */
    @GetMapping("/sample/1")
    public String showSample(){
        return "sample/sample";
    }

    /*
    데모페이지 이동
     */
    @GetMapping("/sample/2")
    public String showDemo(){
        return "sample/sample2";
    }

    /*
    프론트 체크용 테스트 데이터 주입(임시)
     */
    @GetMapping("/make/test/data")
    public String makeTestData() {

//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//        Member testMember = Member.createMember(
//                "user1",
//                passwordEncoder.encode("1"),
//                "Member1",
//                "Member1",
//                "email@email.com"
//        );

//        memberService.save(testMember);


        articleService.makeTestData();
        // 회원 테스트
        // 기본 회원 5명 생성, 로그인아이디 user1, 로그인비밀번호 1  이렇게 user5까지 만듬
        // 가끔 꼬여서 데이터가 두번 들어갈 때가 있습니다.
        memberService.makeTestData();

        // 게시판 테스트
        boardService.makeTestData();


        // 게시글 테스트
        // 게시글 작성한 멤버는 user1로 고정.
        Member testMember = memberService.findByLoginId("user1");
        //articleService.makeTestData(testMember);

        return "redirect:/";
    }



}