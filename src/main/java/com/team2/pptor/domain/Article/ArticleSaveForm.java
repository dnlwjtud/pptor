package com.team2.pptor.domain.Article;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class ArticleSaveForm {

    @NotBlank
    private String title;

    @NotBlank
    private String markdown;

    @NotBlank
    private String html;

}
