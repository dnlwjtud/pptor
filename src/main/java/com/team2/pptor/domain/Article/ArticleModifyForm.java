package com.team2.pptor.domain.Article;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class ArticleModifyForm {

    @NotBlank
    private String title;

    @NotBlank
    private String body;

}