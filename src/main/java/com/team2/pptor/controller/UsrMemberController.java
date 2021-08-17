package com.team2.pptor.controller;

import com.team2.pptor.domain.Member;
import com.team2.pptor.service.MemberService;
import com.team2.pptor.vo.MemberForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

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
    로그인
     */
    @GetMapping("usr/member/doLogin")
    public String doLogin() {
        return "redirect:/";
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
        Member member = new Member();

        member.builder()
                .loginId(memberForm.getLoginId())
                .loginPw(memberForm.getLoginPw())
                .name(memberForm.getName())
                .nickname(memberForm.getNickName())
                .email(memberForm.getEmail());

        memberService.join(member);

        return "redirect:/";
    }

    /*
    회원정보수정 페이지 이동
     */
    @GetMapping("usr/member/modify")
    public String showModify(){
        return "usr/member/modify";
    }

    /*
    회원정보수정
    */
    @PostMapping("usr/member/doModify")
    public String doModify(MemberForm memberForm){
        Member member = new Member();

        member.builder()
                .loginId(memberForm.getLoginId())
                .loginPw(memberForm.getLoginPw())
                .name(memberForm.getName())
                .nickname(memberForm.getNickName())
                .email(memberForm.getEmail());

        memberService.modify(member);

        return "redirect:/";
    }

    /*
    로그아웃
    */
    @GetMapping("usr/member/doLogout")
    public String doLogout(){

        return "redirect:/";
    }

    /*
    회원탈퇴
    */
    //미완
    @GetMapping("usr/member/doDelete")
    public String doDelete(MemberForm memberForm){
        Member member = new Member();

        memberService.delete(member);

        return "redirect:/";
    }
    
    /*
    마이페이지 이동
     */
    @GetMapping("usr/member/myPage")
    public String showMyPage(){

        return "usr/member/myPage";
    }

}
