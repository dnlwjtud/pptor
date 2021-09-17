package com.team2.pptor.service;

import com.team2.pptor.domain.Member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;

    @Test
    public void checkLoginIdDupleTest() {

    // 테스트 멤버 생성
        Member member1 = Member.createMember(
                "user1",
                "1",
                "회원1",
                "user1",
                "test@test.com",
                "11"
        );

        // 회원 저장
        memberService.save(member1);

        //검증
        boolean isSignedAccount = memberService.checkLoginId("user1");

        assertThat(isSignedAccount).isEqualTo(true);


    }

    @Test
    public void checkEmailDupleTest() {

        // 테스트 멤버 생성
        Member member1 = Member.createMember(
                "user1",
                "1",
                "회원1",
                "user1",
                "test@test.com",
                "11"
        );

        // 회원 저장
        memberService.save(member1);

        //검증
        Boolean isSignedAccount = memberService.checkEmail("test@test.com");

        assertThat(isSignedAccount).isEqualTo(true);

    }

    @Test
    public void checkNickDupleTest() {

        // 테스트 멤버 생성
        Member member1 = Member.createMember(
                "user1",
                "1",
                "회원1",
                "user1",
                "test@test.com",
                "11"
        );

        // 회원 저장
        memberService.save(member1);

        //검증
        Boolean isSignedNickname = memberService.checkNick("user1");

        assertThat(isSignedNickname).isEqualTo(true);

    }

}