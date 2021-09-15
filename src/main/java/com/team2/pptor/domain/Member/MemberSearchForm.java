package com.team2.pptor.domain.Member;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MemberSearchForm {

    private String searchType;

    private String searchKeyword;
}
