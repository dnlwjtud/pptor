package com.team2.pptor.controller;

import com.team2.pptor.domain.Member.Member;
import com.team2.pptor.domain.Member.MemberLoginForm;
import com.team2.pptor.security.CustomUserDetails;
import com.team2.pptor.service.MemberService;
import com.team2.pptor.domain.Member.MemberSaveForm;
import com.team2.pptor.domain.Member.MemberModifyForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import java.security.Principal;

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
            log.info("ERROR ::  {}", e.getMessage());
            return "usr/member/join";
        }

        return "redirect:/";

    }

    /*
    회원정보수정 페이지 이동
     */
    @GetMapping("usr/member/modify")
    public String showModify(HttpServletRequest request, Model model, Principal principal){

        Member member;

        try {
            member = memberService.findByLoginId(principal.getName());
        } catch ( Exception e ) {
            log.info("ERROR :: {}", e.getMessage());
            return "redirect:/";
        }

        model.addAttribute("member", member);

        return "usr/member/modify";

    }

    /*
    회원정보수정
    */
    @PostMapping("usr/member/modify")
    public String doModify(@Validated @ModelAttribute MemberModifyForm memberModifyForm, BindingResult bindingResult){

        if ( bindingResult.hasErrors() ) {
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
    public String doDelete(MemberSaveForm memberSaveForm, Principal principal){

        try {
            memberService.delete(principal.getName());
            // 회원정보 삭제 후, Security Context Holder에 저장된 정보 지우기(로그아웃)
            SecurityContextHolder.clearContext();
        } catch ( Exception e ) {
            log.info("ERROR : {}",e.getMessage());
            return "redirect:/";
        }

        return "redirect:/";
    }
    
    /*
    마이페이지 이동
     */
    @GetMapping("usr/member/myPage")
    public String showMyPage(@AuthenticationPrincipal CustomUserDetails user, Principal principal){

        // @AuthenticationPrincipal CustomUserDetails user 를 위와 같이 넣어주고 정보를 빼주면 된다.
        // loginId를 꺼낼땐 getUsername()으로 꺼내는것 주의!!(Override 해야하는 부분이라 loginId로 지정해둠)

        // 테스트 출력용 입니다.
        // 로그인 후 마이페이지로 이동시 콘솔에 출력
        System.out.println("principal로 꺼낸 로그인아이디 : " + principal.getName());
        System.out.println("===========================================");
        System.out.println("UserDetails로 꺼낸 로그인아이디 : " + user.getUsername());
        System.out.println("UserDetails로 꺼낸 이름 : " + user.getName());
        System.out.println("UserDetails로 꺼낸 닉네임 : " + user.getNickname());
        System.out.println("UserDetails로 꺼낸 이메일 : " + user.getEmail());
        System.out.println("UserDetails로 꺼낸 권한 : " + user.getAuthorities());

        return "usr/member/myPage";
    }

}
