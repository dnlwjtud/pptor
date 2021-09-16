package com.team2.pptor.api.controller;


import com.team2.pptor.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    /*
    아이디 중복체크
     */
    @PostMapping("/api/members/check/id/{loginId}")
    public Boolean checkLoginId(@PathVariable( name = "loginId" )String loginId) {
        return memberService.checkLoginId(loginId);
    }

    /*
    이메일 중복체크
     */
    @PostMapping("/api/members/check/email/{email}")
    public Boolean checkEmail(@PathVariable(name="email") String email) {
        return memberService.checkEmail(email);
    }

}
