package com.team2.pptor.domain.Member;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MemberModifyForm {

    @NotBlank
    private String loginId;

    @NotBlank
    private String loginPw;

    @NotBlank
    private String name;

    @NotBlank
    private String nickname;

    @NotBlank
    private String email;

}
