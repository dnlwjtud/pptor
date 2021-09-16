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
    @PostMapping("/api/idCheck/{loginId}")
    public ResponseEntity<Boolean> checkLoginId(@PathVariable String loginId) {
        return ResponseEntity.ok(memberService.checkLoginId(loginId));
    }

    /*
    이메일 중복체크
     */
//    @PostMapping("/api/emailCheck/{email}")
//    public ResponseEntity<Boolean> checkEmail(@PathVariable String email) {
//        return ResponseEntity.ok(memberService.checkEmail(email));
//    }

}
