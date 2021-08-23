package com.team2.pptor.domain.Member;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MemberLoginForm {

    @NotBlank
    private String loginId;

    @NotBlank
    private String loginPw;

}
