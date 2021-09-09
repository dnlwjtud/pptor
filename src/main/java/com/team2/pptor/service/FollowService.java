package com.team2.pptor.service;

import com.team2.pptor.domain.Member.Member;
import com.team2.pptor.domain.follow.Follow;
import com.team2.pptor.repository.FollowRepository;
import com.team2.pptor.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    /*
    팔로우 정보 삭제
     */
    public void delete(String toMemberId) {

        Member toMember = memberRepository.findByLoginId(toMemberId).get();

        Follow findFollow = followRepository.findFollowByToMemberLoginId(toMember.getLoginId());

        //Follow findFollow = followRepository.findFollowByToMember(toMember);

        followRepository.delete(findFollow);

    }

    /*
    팔로우 정보 저장
     */
    public Follow save(String fromMemberId, String toMemberId) {

        // 임시
        Member fromMember = memberRepository.findByLoginId(fromMemberId).get();

        Member toMember = memberRepository.findByLoginId(toMemberId).get();

        return followRepository.save(Follow.createFollow(fromMember, toMember));

    }

    /*
    받은 팔로우 리스트 조회
     */
    public List<Follow> findFollowsByLoginId(Member toMember) {
        return followRepository.findFollowsByToMember(toMember);
    } 

}
