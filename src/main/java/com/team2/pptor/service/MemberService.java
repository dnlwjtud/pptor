package com.team2.pptor.service;

import com.team2.pptor.domain.Member;
import com.team2.pptor.repository.MemberRepository;
import com.team2.pptor.vo.ModifyForm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /*
    회원가입
     */
    @Transactional
    public void save(Member member) {
        checkDuplicate(member); // 회원중복확인
        memberRepository.save(member);
    }

    /*
    회원 아이디 중복확인 메소드
      */
    private void checkDuplicate(Member member) {

        Optional<Member> memberOptional = memberRepository.findByLoginId(member.getLoginId());

        // 수정필
        if ( !memberOptional.isEmpty() ) {
            throw new IllegalStateException( "이미 존재하는 계정입니다." );
        }

    }

    /*
    회원 정보 수정
     */
    @Transactional
    public void modify(ModifyForm modifyForm) {

        Optional<Member> memberOptional = memberRepository.findByLoginId(modifyForm.getLoginId());

        memberOptional.ifPresent(
                member -> member.changeMemberInfo(
                        modifyForm.getLoginPw(),
                        modifyForm.getNickName(),
                        modifyForm.getEmail()
                )
        );

        if ( memberOptional.isEmpty() ) {
            throw new IllegalStateException("해당 회원을 찾을 수 없습니다");
        }

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

        Optional<Member> memberOptional = memberRepository.findByLoginId(loginId);

        // 수정필
        if (memberOptional.get().getLoginPw().equals(loginPw)) {
            return memberOptional.get();
        } else {
            throw new IllegalStateException("아이디/비밀번호가 일치하지 않습니다.");
        }

    }

}


