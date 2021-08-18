package com.team2.pptor.controller;

import com.team2.pptor.domain.Member;
import com.team2.pptor.service.MemberService;
import com.team2.pptor.vo.LoginForm;
import com.team2.pptor.vo.MemberForm;
import com.team2.pptor.vo.ModifyForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    @PostMapping("usr/member/doLogin")
    public String doLogin(LoginForm loginForm, HttpServletRequest request) {

        Member logonMember = memberService.checkMember(loginForm.getLoginId(), loginForm.getLoginPw());

        if ( logonMember == null ) {
            // 임시 콘솔 출력용
            System.out.println("로그인 실패 확인요망");
            return "redirect:/";
        }

        HttpSession session = request.getSession();

        session.setAttribute("logonMember", logonMember);

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

        Member member = Member.createMember(memberForm.getLoginId(),
                memberForm.getLoginPw(),
                memberForm.getName(),
                memberForm.getNickName(),
                memberForm.getEmail());

        memberService.join(member);

        return "redirect:/";
    }

    /*
    회원정보수정 페이지 이동
     */
    @GetMapping("usr/member/modify")
    public String showModify(HttpServletRequest request, Model model){

        Member logonMember  = (Member) request.getAttribute("logonMember");


        model.addAttribute("member",logonMember);

        return "usr/member/modify";
    }

    /*
    회원정보수정
    */
    @PostMapping("usr/member/doModify")
    public String doModify(ModifyForm modifyForm){

        memberService.modify(modifyForm);

        return "redirect:/";
    }

    /*
    로그아웃
    */
    @RequestMapping("usr/member/doLogout")
    public String doLogout(HttpServletRequest request){

        HttpSession session = request.getSession(false);

        session.invalidate();

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
    public String showMyPage(HttpServletRequest request){

        /*
        로그인 세션 유효성 확인용 임시
         */
        HttpSession session = request.getSession();

        System.out.println(session.getAttribute("logonMember"));

        return "usr/member/myPage";
    }

}
