package com.team2.pptor.api.controller;

import com.team2.pptor.security.CustomUserDetails;
import com.team2.pptor.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ArticleApiController {

    private final ArticleService articleService;

    @DeleteMapping("/api/articles/{id}")
    @ResponseBody
    public String deleteArticle(@PathVariable(name = "id")Long id, @AuthenticationPrincipal CustomUserDetails user ) {

        try {
            articleService.delete(id, user);
            return "OK";
        } catch ( Exception e ) {
            log.info("ERROR :: " + e.getMessage());
            return "No";
        }

    }



}
