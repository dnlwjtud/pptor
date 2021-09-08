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
@RequestMapping("/api")
public class FollowApiController {

    private final FollowService followService;

    /*
    팔로우 메소드
     */
    @PostMapping("/follow/{toMemberId}")
    public Follow followMember(@PathVariable String toMemberId, @AuthenticationPrincipal CustomUserDetails user) {
        return followService.save(user.getName(), toMemberId);
    }
    
    /*
    언팔로우 메소드
     */
    @DeleteMapping("/follow/{toMemberId}")
    public void unFollowMember(@PathVariable String toMemberId, @AuthenticationPrincipal CustomUserDetails user) {
        followService.delete(toMemberId);
    }

}
