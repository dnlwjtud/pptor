package com.team2.pptor.service;

import com.team2.pptor.domain.Member;
import com.team2.pptor.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /*
    회원가입
     */
    @Transactional
    public void join(Member member) {
        checkDuplicate(member); // 회원중복확인
        memberRepository.join(member);
    }

    /*
    회원 아이디 중복확인 메소드
      */
    private void checkDuplicate(Member member) {
        List<Member> members = memberRepository.getMemberByMemberLoginId(member.getLoginId());

        if(!members.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

    }

    /*
    회원 정보 수정
     */
    @Transactional
    public void modify() {
    }

    /*
    회원탈퇴
     */
    @Transactional
    public void delete(Member member) {
        memberRepository.delete(member);
    }

    /*
    로그인 정보 체크 메소드
     */
    public Member checkMember(String loginId, String loginPw) {

        List<Member> findMembers = memberRepository.getMemberByMemberLoginId(loginId);

        if ( findMembers.isEmpty() ) {
            return null;
        }

        Member findMember = findMembers.get(0);

        if ( findMember.getLoginPw().equals(loginPw) ) {
            return findMember;
        } else {
            return null;
        }

    }
}


