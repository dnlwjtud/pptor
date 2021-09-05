
package com.team2.pptor.controller;

import com.team2.pptor.domain.Member.Member;
import com.team2.pptor.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final MemberService memberService;


    @GetMapping("/page/{loginId}")
    public String showMyPage(@PathVariable(name = "loginId") String loginId, Principal principal, Model model) {

        if ( !principal.getName().equals(loginId) ) {
            log.info("ERROR : 권한이 없는 시도를 하였습니다.");
            return "redirect:/";
        }

        Member findMember = memberService.findByLoginId(loginId);

        model.addAttribute("member", findMember);

        return "usr/myPage";
    }

}
