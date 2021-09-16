package com.team2.pptor.api.controller;

import com.team2.pptor.domain.Member.Member;
import com.team2.pptor.security.CustomUserDetails;
import com.team2.pptor.service.FavoriteService;
import com.team2.pptor.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
public class FavoriteApiController {

    private final FavoriteService favoriteService;
    private final MemberService memberService;

    @PostMapping("/api/favorite/{articleId}")
    public String addFavorite(@PathVariable(name="articleId") Long articleId, @AuthenticationPrincipal CustomUserDetails user){

        Member member = memberService.findByLoginId(user.getUsername());

        favoriteService.save(member, articleId);

        return "redirect:/";
    }

}
