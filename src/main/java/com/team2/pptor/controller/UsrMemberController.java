package com.team2.pptor.controller;

import com.team2.pptor.domain.Member.Member;
import com.team2.pptor.domain.Member.MemberLoginForm;
import com.team2.pptor.service.MemberService;
import com.team2.pptor.domain.Member.MemberSaveForm;
import com.team2.pptor.domain.Member.MemberModifyForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
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
    public String showLogin(Model model) {

        model.addAttribute("memberLoginForm", new MemberLoginForm());

        return "usr/member/login";
    }

    /*
    회원가입 페이지 이동
     */
    @GetMapping("usr/member/join")
    public String showJoin(Model model) {

        // 리다이렉트를 받기 위한  폼 객체 생성
        model.addAttribute("memberSaveForm",new MemberSaveForm());

        return "usr/member/join";
    }

    /*
    회원가입
     */
    @PostMapping("usr/member/join")
    public String doJoin(@Validated @ModelAttribute MemberSaveForm memberSaveForm, BindingResult bindingResult) {

        // 오류가 확인되어 바인딩 되었다면
        if ( bindingResult.hasErrors() ) {
            // 로그에 표기와 같이 표기
            log.info("ERRORS={}",bindingResult);
            return "usr/member/join";
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Member member = Member.createMember(memberSaveForm.getLoginId(),
                passwordEncoder.encode(memberSaveForm.getLoginPw()),
                memberSaveForm.getName(),
                memberSaveForm.getNickName(),
                memberSaveForm.getEmail()
        );

        try {
            memberService.save(member);
        } catch ( Exception e ) {
            log.info("ERRORS={}", e);
            return "usr/member/join";
        }

        return "redirect:/";

    }

    /*
    회원정보수정 페이지 이동
     */
    @GetMapping("usr/member/modify")
    public String showModify(HttpServletRequest request, Model model){

        /*
        Member logonMember  = (Member) request.getAttribute("logonMember");

        System.out.println(logonMember.getId());
        // NPE 이슈 발생
        // dnlwjtud1
        model.addAttribute("member",logonMember);
        */

        //System.out.println(request.getSession());

        return "usr/member/modify";
    }

    /*
    회원정보수정
    */
    @PostMapping("usr/member/modify")
    public String doModify(@Validated @ModelAttribute MemberModifyForm memberModifyForm, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            log.info("ERRORS={}",bindingResult);
            return "usr/member/modify";
        }

        memberService.modify(memberModifyForm);

        return "redirect:/";

    }

    /*
    로그아웃
    */
    /*
    @RequestMapping("usr/member/doLogout")
    public String doLogout(HttpServletRequest request){

        HttpSession session = request.getSession(false);

        session.invalidate();

        return "redirect:/";
    }
    */

    /*
    회원탈퇴
    */
    @GetMapping("usr/member/doDelete")
    public String doDelete(MemberSaveForm memberSaveForm){
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
