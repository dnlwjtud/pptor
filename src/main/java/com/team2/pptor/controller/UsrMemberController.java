package com.team2.pptor.controller;

import com.team2.pptor.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsrMemberController {
    private MemberService memberService;

    @Autowired
    public UsrMemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    //로그인
    @GetMapping("usr/member/login")
    public String loginPage() {
        return "login";
    }

}
