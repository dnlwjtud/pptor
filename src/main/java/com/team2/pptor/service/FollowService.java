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
import java.util.NoSuchElementException;

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
    public boolean delete(String toMemberId, String fromMemberId) {

        // 상대방 마이페이지(toMember)
        // 상대방 팔로우 목록에 내가 있으면? => 삭제
        // 상대방 팔로우 목록에 내가 없으면? => 오류

        // 필요한것
        // 나, 상대방, 상대방 팔로우 목록, 내 팔로우

        // 상대방
        Member toMember = memberRepository.findByLoginId(toMemberId).get();

        // 상대방 팔로우 목록 toMemberFollow<list>
        List<Follow> followsByToMember = followRepository.findFollowsByToMember(toMember);

        for (Follow follow : followsByToMember) {
            System.out.println("팔로우 하고자 하는 사람의 정보 : " + toMember.getLoginId());
            System.out.println("팔로우 한 사람의 정보 : " + follow.getFromMember());

            // 내가 한 팔로우가 있을때? => 삭제
            if ( follow.getFromMember().getLoginId().equals(fromMemberId) ) {
                followRepository.delete(follow);
                return true;
            } else {
                continue;
            }

        }

        return false;

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

    /*
    팔로우 여부 확인하기

    /page/admin

    super -> admin 팔로우 => true
    super -> admin 언팔 => false

    super -> admin 팔로우여부?
     */
    public boolean checkFollow(String toMemberLoginId, Member fromMember) {

        List<Follow> currentFollows = followRepository.findFollowsByFromMember(fromMember);

        for (Follow currentFollow : currentFollows) {

            if ( currentFollow.getToMember().getLoginId().equals(toMemberLoginId) ) {
                return true;
            } else {
                return false;
            }

        }

        return false;
    }

}
