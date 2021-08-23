package com.team2.pptor.domain.Member;

import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class MemberSaveForm {

    @NotBlank
    private String loginId;

    @NotBlank
    private String loginPw;

    @NotBlank
    private String name;

    @NotBlank
    private String nickName;

    @NotBlank
    private String email;

}
