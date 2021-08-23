package com.team2.pptor.domain.Member;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ModifyForm {

    private String loginId;

    @NotBlank
    private String loginPw;
    @NotBlank
    private String nickName;
    @NotBlank
    private String email;

}
