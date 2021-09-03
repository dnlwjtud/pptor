package com.team2.pptor.domain.Board;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BoardModifyForm {

    private int id;

    @NotBlank
    private String name;
}
