package com.team2.pptor.api.controller;

import com.team2.pptor.domain.Member.Member;
import com.team2.pptor.security.CustomUserDetails;
import com.team2.pptor.service.FavoriteService;
import com.team2.pptor.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/api/favorite/{articleId}")
    public String delFavorite(@PathVariable(name="articleId") Long articleId, @AuthenticationPrincipal CustomUserDetails user){

        Member member = memberService.findByLoginId(user.getUsername());

        favoriteService.delete(member, articleId);

        return "redirect:/";
    }

    @PutMapping("/api/favorite/{articleId}")
    public String modifyFavorite(@PathVariable(name="articleId") Long articleId, @AuthenticationPrincipal CustomUserDetails user){

        Member member = memberService.findByLoginId(user.getUsername());

        favoriteService.modifyFavorite(member, articleId);

        return "redirect:/";
    }

}
