package com.team2.pptor.service;

import com.team2.pptor.domain.Member.Member;
import com.team2.pptor.domain.follow.Follow;
import com.team2.pptor.repository.FollowRepository;
import com.team2.pptor.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class FollowServiceTest {

    @Autowired FollowService followService;
    @Autowired FollowRepository followRepository;
    @Autowired MemberRepository memberRepository;

    @Test
    public void saveTest() {

        // 팔로우 하는 멤버 생성
        Member fromMember = Member.createMember(
                "user1",
                "1",
                "회원1",
                "user1",
                "test@test.com",
                "11"
        );

        // 팔로우 받는 멤버 생성
        Member toMember = Member.createMember(
                "user2",
                "1",
                "회원2",
                "user2",
                "test@test.com",
                "11"
        );

        // 회원 저장
        Member savedFromMember = memberRepository.save(fromMember);
        Member savedToMember = memberRepository.save(toMember);

        // 회원 불러오기
        Member findFromMember = memberRepository.findByLoginId(savedFromMember.getLoginId()).get();
        Member findToMember = memberRepository.findByLoginId(savedToMember.getLoginId()).get();

        // 좋아요 생성
        Follow savedFollow = followService.save(findFromMember.getLoginId(), findToMember.getLoginId());

        System.out.println(savedFollow.toString());

    }

    @Test
    public void deleteTest() {

        // 팔로우 하는 멤버 생성
        Member fromMember = Member.createMember(
                "user1",
                "1",
                "회원1",
                "user1",
                "test@test.com",
                "11"
        );

        // 팔로우 받는 멤버 생성
        Member toMember = Member.createMember(
                "user2",
                "1",
                "회원2",
                "user2",
                "test@test.com",
                "11"
        );

        // 회원 저장
        Member savedFromMember = memberRepository.save(fromMember);
        Member savedToMember = memberRepository.save(toMember);

        // 회원 불러오기
        Member findFromMember = memberRepository.findByLoginId(savedFromMember.getLoginId()).get();
        Member findToMember = memberRepository.findByLoginId(savedToMember.getLoginId()).get();

        // 좋아요 생성
        Follow savedFollow = followService.save(findFromMember.getLoginId(), findToMember.getLoginId());

        // 팔로우 삭제
        followService.delete(findToMember.getLoginId());

        // 검증
        List<Follow> followList = followRepository.findAll();
        assertThat(followList.size()).isEqualTo(0);

    }

}