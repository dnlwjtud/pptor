package com.team2.pptor.controller;

import com.team2.pptor.domain.Member;
import com.team2.pptor.domain.MemberForm;
import com.team2.pptor.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsrMemberController {

    private MemberService memberService;

    @Autowired
    public UsrMemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /*
    로그인 페이지 이동
     */
    @GetMapping("usr/member/login")
    public String showLogin() {
        return "usr/member/login";
    }

    /*
    회원가입 페이지 이동
     */
    @GetMapping("usr/member/join")
    public String showJoin() {
        return "usr/member/join";
    }
    
    /*
    회원가입
     */
    @PostMapping("usr/member/doJoin")
    public String doJoin(MemberForm memberForm){

        // 멤버 객체생성
        Member member = new Member();

        // 빌더패턴을 통한 값 설정
        member.builder()
                .loginId(memberForm.getLoginId())
                .loginPw(memberForm.getLoginPw())
                .name(memberForm.getName())
                .nickname(memberForm.getNickName())
                .email(memberForm.getEmail());

        memberService.add(member);


        return "redirect:/";
    }

}
