package com.team2.pptor.controller;

import com.team2.pptor.domain.Member.Member;
import com.team2.pptor.domain.Member.MemberLoginForm;
import com.team2.pptor.mail.MailService;
import com.team2.pptor.security.CustomUserDetails;
import com.team2.pptor.service.MemberService;
import com.team2.pptor.domain.Member.MemberSaveForm;
import com.team2.pptor.domain.Member.MemberModifyForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UsrMemberController {

    private final MemberService memberService;
    private final MailService mailService;


    /*
    로그인 페이지 이동
     */
    @GetMapping("/members/login")
    public String showLogin(Model model) {

        model.addAttribute("memberLoginForm", new MemberLoginForm());

        return "usr/member/login";
    }

    /*
    회원가입 페이지 이동
     */
    @GetMapping("/members/join")
    public String showJoin(Model model) {

        // 리다이렉트를 받기 위한  폼 객체 생성
        model.addAttribute("memberSaveForm",new MemberSaveForm());

        return "usr/member/join";
    }

    /*
    회원가입
     */
    @PostMapping("/members/join")
    public String doJoin(@Validated @ModelAttribute MemberSaveForm memberSaveForm, BindingResult bindingResult) {

        // 오류가 확인되어 바인딩 되었다면
        if ( bindingResult.hasErrors() ) {
            // 로그에 표기와 같이 표기
            log.info("ERRORS={}",bindingResult);
            return "usr/member/join";
        }

        String authKey = mailService.sendMailWithAuth(memberSaveForm.getEmail());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Member member = Member.createMember(memberSaveForm.getLoginId(),
                passwordEncoder.encode(memberSaveForm.getLoginPw()),
                memberSaveForm.getName(),
                memberSaveForm.getNickName(),
                memberSaveForm.getEmail(),
                authKey
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
    @GetMapping("/members/{loginId}")
    public String showModify(@PathVariable(name = "loginId") String loginId,  Model model, Principal principal){

        if ( !loginId.equals(principal.getName()) ) {
            return "redirect:/";
        }

        try {
            
            Member member = memberService.findByLoginId(principal.getName());

            MemberModifyForm memberModifyForm = new MemberModifyForm();

            // 임시
            memberModifyForm.setLoginId(member.getLoginId());
            memberModifyForm.setLoginPw(member.getLoginPw());
            memberModifyForm.setName(member.getName());
            memberModifyForm.setNickname(member.getNickname());
            memberModifyForm.setEmail(member.getEmail());

            model.addAttribute("memberModifyForm", memberModifyForm);

            return "usr/member/modify";

        } catch ( Exception e ) {
            log.info("ERROR :: {}", e.getMessage());
            return "redirect:/";
        }

    }

    /*
    회원정보수정
    */
    @PutMapping("/members/{loginId}")
    public String doModify(@PathVariable(name = "loginId") String loginId, @Validated @ModelAttribute MemberModifyForm memberModifyForm, BindingResult bindingResult, Model model, Principal principal){

        if ( !loginId.equals(principal.getName()) ) {
            return "redirect:/";
        }

        if ( bindingResult.hasErrors() ) {
            log.info("ERRORS={}",bindingResult);
            return "usr/member/modify";
        }

        model.addAttribute("memberModifyForm",memberModifyForm);

        memberService.modify(memberModifyForm);

        return "redirect:/";

    }


    /*
    회원탈퇴
    */
    @DeleteMapping("/members/{loginId}")
    public String doDelete(@PathVariable(name = "loginId") String loginId, Principal principal, @AuthenticationPrincipal CustomUserDetails user){

        if ( !loginId.equals(principal.getName()) && !user.getAuthorities().toString().contains("ROLE_ADMIN") ) {
            return "redirect:/";
        }


        try {
            memberService.delete(loginId);
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
    @GetMapping("/myPage")
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


    // 비밀번호 찾기
    @GetMapping("/members/findPw")
    public String showFindPw(){
        return "/usr/member/findPw";
    }

    @PostMapping("/findPw")
    public String findPw(
            @RequestParam("loginId") String loginId,
            @RequestParam("email") String email
    ){
        memberService.findLoginPw(loginId, email);

        return "redirect:/";
    }

}
