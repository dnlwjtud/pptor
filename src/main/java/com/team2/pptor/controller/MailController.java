package com.team2.pptor.controller;

import com.team2.pptor.mail.MailService;
import com.team2.pptor.security.CustomUserDetails;
import com.team2.pptor.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;
    private final MemberService memberService;

    @GetMapping("/mail/auth")
    public String checkAuth(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam("authKey") String authKey){

        // checkAuth 의 값이 true인지 false인지 확인
        if(memberService.checkAuth(user, authKey) == false){
            return "/error/5xx";  // false인 경우(인증키 불일치)
        }

        System.out.println("인증키 일치");
        return "redirect:/"; // true인 경우(인증키 일치)
    }

    // 텍스트 메일보내기 테스트용 입니다.
    @GetMapping("/mail/send/text")
    public String sendMail(){

        mailService.sendMail("받는사람메일", "test", "bodyTest");

        return "redirect:/";
    }

    // 이미지파일 메일보내기 테스트용 입니다.
    @GetMapping("/mail/send/img")
    public String sendImg(){

        mailService.sendMailWithImg("받는사람메일", "test", "bodyTest", "/static/img/logo-b.png");

        return "redirect:/";
    }

    // 파일첨부 메일보내기 테스트용 입니다.
    @GetMapping("/mail/send/file")
    public String sendHtml(){

        mailService.sendMailWithFile("받는사람메일", "test", "bodyTest", "/static/error/500.html");

        return "redirect:/";
    }
}
