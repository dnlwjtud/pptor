package com.team2.pptor.domain.Article;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class Content {

    @NotBlank
    private String code;

    @NotNull
    private List<String> contentTexts;

}
