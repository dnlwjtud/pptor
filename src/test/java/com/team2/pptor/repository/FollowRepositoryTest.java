package com.team2.pptor.repository;

import com.team2.pptor.domain.Member.Member;
import com.team2.pptor.domain.follow.Follow;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
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
        assertThat(findFollow).isEqualTo(savedFollow);

    }

    @Test
    public void followersListTest() {

        // 팔로우 하는 멤버 생성
        Member fromMember1 = Member.createMember(
                "user1",
                "1",
                "팔로우 하는 사람 1",
                "user1",
                "test@test.com",
                "11"
        );

        // 팔로우 하는 멤버 생성
        Member fromMember2 = Member.createMember(
                "user3",
                "3",
                "팔로우 하는 사람 2",
                "user3",
                "test@test.com",
                "11"
        );

        // 팔로우 받는 멤버 생성
        Member toMember = Member.createMember(
                "user2",
                "1",
                "팔로우 받는 사람 1",
                "user2",
                "test@test.com",
                "11"
        );

        // 회원 저장
        Member savedFromMember1 = memberRepository.save(fromMember1);
        Member savedFromMember2 = memberRepository.save(fromMember2);
        Member savedToMember = memberRepository.save(toMember);

        // 회원 불러오기
        Member findFromMember1 = memberRepository.findByLoginId(savedFromMember1.getLoginId()).get();
        Member findFromMember2 = memberRepository.findByLoginId(savedFromMember2.getLoginId()).get();
        Member findToMember = memberRepository.findByLoginId(savedToMember.getLoginId()).get();

        // 좋아요 생성
        Follow follow1 = Follow.createFollow(findFromMember1,findToMember);
        Follow follow2 = Follow.createFollow(findFromMember2,findToMember);

        // 좋아요 저장
        Follow savedFollow1 = followRepository.save(follow1);
        Follow savedFollow2 = followRepository.save(follow2);

        // 팔로우 받은 멤버로 팔로우 정보 불러오기
        List<Follow> followList1 = followRepository.findFollowsByToMember(findToMember);
        List<Follow> followList2 = followRepository.findFollowsByToMember(findFromMember2);

        // 검증
        assertThat(followList1.size()).isEqualTo(2);
        assertThat(followList2.size()).isEqualTo(0);

        System.out.println(followList1);
        System.out.println(followList2);


        //for (Follow follow : followList) {
            /*
            System.out.println("팔로우 한 유저 정보 : " + follow.getFromMember().toString());
            System.out.println("팔로우 받은 유저 정보 : " + follow.getToMember().toString());
             */
          //  System.out.println(follow.toString());
        //}


    }

    @Test
    public void  existsFollowByMemberTest(){

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

        // 좋아요 찾기


        // 검증
        //boolean isFollow = followRepository.existsFollowByMember(fromMember);
        //boolean isFollow = followRepository.existsFollowByMemberId(fromMember.getId());
        //assertThat(isFollow).isEqualTo(true);

    }

}