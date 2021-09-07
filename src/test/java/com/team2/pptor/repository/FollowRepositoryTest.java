package com.team2.pptor.repository;

import com.team2.pptor.domain.Member.Member;
import com.team2.pptor.domain.follow.Follow;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class FollowRepositoryTest {

    @Autowired FollowRepository followRepository;
    @Autowired MemberRepository memberRepository;

    @Test
    public void findFollowByToMemberTest() {

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
        Follow follow1 = Follow.createFollow(findFromMember,findToMember);
        
        // 좋아요 저장
        Follow savedFollow = followRepository.save(follow1);

        // 좋아요 아이디로서 조회
        Follow findFollow = followRepository.findFollowByToMemberLoginId(toMember.getLoginId());

        // 검증
        Assertions.assertThat(findFollow).isEqualTo(savedFollow);

    }

}