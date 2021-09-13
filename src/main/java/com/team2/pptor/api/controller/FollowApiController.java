package com.team2.pptor.api.controller;

import com.team2.pptor.domain.follow.Follow;
import com.team2.pptor.security.CustomUserDetails;
import com.team2.pptor.service.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@RestController
public class FollowApiController {

    private final FollowService followService;

    /*
    팔로우 메소드
     */
    @PostMapping("/api/follow/{toMemberId}")
    public String followMember(@PathVariable String toMemberId, @AuthenticationPrincipal CustomUserDetails user) {

        followService.save(user.getUsername(), toMemberId);

        return "OK";
    }
    
    /*
    언팔로우 메소드
     */
    @DeleteMapping("/api/follow/{toMemberId}")
    public void unFollowMember(@PathVariable String toMemberId, @AuthenticationPrincipal CustomUserDetails user) {

        System.out.println("로그인한 유저의 아이디 : " + user.getUsername());
        followService.delete(toMemberId, user.getUsername());
    }

}
