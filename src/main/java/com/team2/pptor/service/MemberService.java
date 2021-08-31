package com.team2.pptor.service;

import com.team2.pptor.domain.Member.Member;
import com.team2.pptor.mail.Mail;
import com.team2.pptor.mail.MailService;
import com.team2.pptor.repository.MemberRepository;
import com.team2.pptor.security.CustomUserDetails;
import com.team2.pptor.security.Role;
import com.team2.pptor.domain.Member.MemberModifyForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.security.SecureRandom;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final HttpSession session;
    private static final java.util.UUID UUID = java.util.UUID.randomUUID();
    private final MemberRepository memberRepository;
    private final MailService mailService;

    /*
    테스트 회원 생성(임시)
     */
    @Transactional
    public void makeTestData() {


        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        for ( int i = 1; i <= 5 ; i++){

            String pw = Integer.toString(i);

            session.setAttribute("CSRF_TOKEN",UUID.randomUUID().toString());
            Member testMember = Member.createMember(
                    "user" + i,
                    passwordEncoder.encode(pw),
                    "회원" + i,
                    "회원" + i,
                    "email" + pw + "@email.com"
            );

            memberRepository.save(testMember);

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

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        memberOptional.ifPresent(
                member -> member.changeMemberInfo(
                        passwordEncoder.encode(memberModifyForm.getLoginPw()),
                        memberModifyForm.getNickname(),
                        memberModifyForm.getEmail()
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
    public void delete(String loginId) {

        Member member;

        try {
            member = memberRepository.findByLoginId(loginId).get();
        } catch ( Exception exception ) {
            throw new IllegalStateException("존재하지 않는 회원입니다. ");
        }

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

    /*
    회원 아이디로 회원 조회
     */
    public Member findByLoginId(String loginId) {

        Optional<Member> memberOptional = memberRepository.findByLoginId(loginId);

        if ( memberOptional.isEmpty() ) {
            throw new IllegalStateException("존재하지 않은 회원입니다.");
        } else {
            return memberOptional.get();
        }

    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        Member memberEntity = findByLoginId(loginId);

        List<GrantedAuthority> authorities = new ArrayList<>();

        if (("aa").equals(loginId)) {  // 로그인 시 권한설정, domain패키지에 Role enum 생성함.

            // 로그인 아이디가 aa일 때 관리자 권한을 부여.
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else {

            // 로그인 아이디가 aa 이외일때 일반 회원으로 권한 부여
            authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        }

        // spring security에서 제공하는 UserDetails를 구현한 User를 반환(org.springframework.security.core.userdetails.User )
        // 원래 반환하는 User(UserDetails를 상속하는 클래스?) 정보는 로그인아이디, 로그인비밀번호, 권한리스트이다.
        // UserDetails를 커스텀함. 로그인한 회원의 회원번호, 로그인아이디, 이름, 닉네임, 이메일, 권한을 담는다.
        return new CustomUserDetails(memberEntity.getId(), memberEntity.getLoginId(), memberEntity.getLoginPw(),
                memberEntity.getName(), memberEntity.getNickname(), memberEntity.getEmail(), authorities);
    }


    // 비밀번호 찾기 임시 구현용입니다.
    // 임시비밀번호 받아서 메일로 보내기기
   public void findLoginPw(String loginId, String email) {
        Member member = findByLoginId(loginId);

        if( !member.getEmail().equals(email) ){
            throw new IllegalStateException("존재하지 않은 회원입니다.");
        }

        String newPw = getRandomPw();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        mailService.sendMail(email, "pptor 임시 비밀번호",
                "임시비밀번호는 " + newPw + " 입니다. 로그인 후 변경해주세요.");

        member.changeMemberInfo(passwordEncoder.encode(newPw), member.getNickname(), member.getEmail());

        memberRepository.modify(member);

    }

    // 임시비밀번호 생성(SecureRandom 사용)
    public String getRandomPw(){
        SecureRandom secureRandom = new SecureRandom();

        String english_lower = "abcdefghijklmnopqrstuvwxyz";
        String english_upper = english_lower.toUpperCase();
        String numbers = "0123456789";

        String stringDatas = english_lower + english_upper + numbers;

        int pwLength = 8;  // 비밀번호 몇 자인지 지정, 현재 8자

        StringBuilder stringBuilder = new StringBuilder(pwLength);

        // charAt(index) 은 index 위치의 문자를 불러온다.
        // String testStr = "가나다라마" 에서 testStr.charAt(2)는 인덱스 2 위치의 '다'를 불러온다.
        for(int i = 0; i < pwLength; i++) {
            stringBuilder.append(stringDatas.charAt(secureRandom.nextInt(stringDatas.length())));
        }
        return stringBuilder.toString();
    }
}


