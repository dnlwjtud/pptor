package com.team2.pptor.domain;

import lombok.Data;

@Data
public class MemberForm {

    private String loginId;
    private String loginPw;
    private String name;
    private String nickName;
    private String email;

}
