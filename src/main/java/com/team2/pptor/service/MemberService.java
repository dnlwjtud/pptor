package com.team2.pptor.service;

import com.team2.pptor.domain.Member.Member;
import com.team2.pptor.domain.Member.MemberContext;
import com.team2.pptor.repository.MemberRepository;
import com.team2.pptor.domain.Member.MemberModifyForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    /*
    테스트 회원 생성(임시)
     */
    @Transactional
    public void makeTestData() {

        for ( int i = 0; i < 5 ; i++){

            Member testMember = Member.createMember(
                    "user" + i,
                    "1",
                    "회원" + i,
                    "회원" + i,
                    "email@email.com"
            );

        }

    }

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
    public void modify(MemberModifyForm memberModifyForm) {

        Optional<Member> memberOptional = memberRepository.findByLoginId(memberModifyForm.getLoginId());

        if ( memberOptional.isEmpty() ) {
            throw new IllegalStateException("해당 회원을 찾을 수 없습니다");
        }

        memberOptional.ifPresent(
                member -> member.changeMemberInfo(
                        memberModifyForm.getLoginPw(),
                        memberModifyForm.getNickName(),
                        memberModifyForm.getEmail()
                )
        );


    }

    /*
    회원탈퇴
     */
    @Transactional
    public void delete(Member member) {
        memberRepository.delete(member);
    }

    /*
    중복체크 메소드
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

    /*
    로그인 메소드
     */
    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        
        // DB에서 Member 객체조회
        Optional<Member> optionalMember = memberRepository.findByLoginId(loginId);
        Member findMember;

        if ( optionalMember.isPresent() ) {
            findMember = optionalMember.get();
        } else {
            throw new UsernameNotFoundException("존재하지 않는 회원입니다.");
        }

        // 권한정보 등록
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(findMember.getMemberRoles().getValue()));

        // 조회용 AccountContext 생성

        return new MemberContext(findMember.getLoginId(), findMember.getLoginPw(), roles);

    }

    /*
    회원 조회 메소드
     */
    public Optional<Member> findByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId);
    }

    /*
    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Optional<Member> memberEntityWrapper = memberRepository.findByLoginId(loginId);
        Member memberEntity = memberEntityWrapper.get();

        List<GrantedAuthority> authorities = new ArrayList<>();

        if (("aa").equals(loginId)) {  // 로그인 시 권한설정, domain패키지에 Role enum 생성함.

            // 로그인 아이디가 aa일 때 관리자 권한을 부여.
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else {

            // 로그인 아이디가 aa 이외일때 일반 회원으로 권한 부여
            authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        }

        // spring security에서 제공하는 UserDetails를 구현한 User를 반환(org.springframework.security.core.userdetails.User )
        // 반환하는 정보는 로그인아이디, 로그인비밀번호, 권한리스트이다.
        return new User(memberEntity.getLoginId(), memberEntity.getLoginPw(), authorities);
    }
    
     */
    

}


