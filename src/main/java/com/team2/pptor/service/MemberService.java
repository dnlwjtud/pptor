package com.team2.pptor.service;

import com.team2.pptor.domain.Member.Member;
import com.team2.pptor.mail.MailService;
import com.team2.pptor.repository.MemberRepository;
import com.team2.pptor.security.CustomUserDetails;
import com.team2.pptor.security.Role;
import com.team2.pptor.domain.Member.MemberModifyForm;
import com.team2.pptor.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        for ( int i = 1; i <= 35 ; i++){

            String pw = Integer.toString(i);

//            session.setAttribute("CSRF_TOKEN",UUID.randomUUID().toString());
            Member testMember = Member.createMember(
                    "user" + i,
                    passwordEncoder.encode(pw),
                    "회원" + i,
                    "회원" + i,
                    "email" + pw + "@email.com",
                    "11"
            );

            memberRepository.saveAndFlush(testMember);

        }

        /*
        메일 인증 완료된 계정 생성(임시)
         */
        Member lv3Member = Member.createTestAuthMember(
                "super",
                passwordEncoder.encode("1"),
                "회원1",
                "테스트",
                "test@test.com",
                3
        );

        Member super2Member = Member.createTestAuthMember(
                "super2",
                passwordEncoder.encode("1"),
                "회원2",
                "테스트2",
                "test@test.com",
                3
        );

        /*
        관리자 계정 생성(임시)
         */
        Member lv7Member = Member.createTestAuthMember(
                "admin",
                passwordEncoder.encode("1"),
                "관리자",
                "관리자",
                "admin@admin.com",
                7
        );


        memberRepository.saveAndFlush(lv3Member);
        memberRepository.saveAndFlush(super2Member);
        memberRepository.saveAndFlush(lv7Member);

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
                        memberModifyForm.getEmail(),
                        member.getAuthLevel()
                )
        );

        if ( memberOptional.isEmpty() ) {
            throw new IllegalStateException("해당 회원을 찾을 수 없습니다");
        }

    }

    @Transactional
    public void modifyInfo(Member member){
        memberRepository.modify(member);
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
    /*
    해당 회원 작성 게시물 개수 조회
     */
    public int getCountByMember(String loginId) {

        Optional<Member> memberOptional = memberRepository.findByLoginId(loginId);

        if ( memberOptional.isEmpty() ) {
            throw new IllegalStateException("존재하지 않은 회원입니다.");
        } else {

            return 1;

        }

    }

    @Transactional
    public Page<Member> getMemberPage(Pageable pageable) {

        return memberRepository.findAll(pageable);

    }

    @Transactional
    public Page<Member> getSearchedMemberPage(Pageable pageable, String searchType ,String searchKeyword) {

        switch (searchType){
            case "loginId":
                return memberRepository.findByLoginIdContaining(pageable, searchKeyword);
            case "nickname":
                return memberRepository.findByNicknameContaining(pageable, searchKeyword);
            case "email":
                return memberRepository.findByEmailContaining(pageable, searchKeyword);
            default:
                return memberRepository.findAll(pageable);
        }

    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        Member memberEntity = findByLoginId(loginId);

        List<GrantedAuthority> authorities = new ArrayList<>();

        if (memberEntity.getAuthLevel() == 7) {  // 로그인 시 권한설정, domain패키지에 Role enum 생성함.

            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
            authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
            // ADMIN에게도 MEMBER 권한을 부여하여 MEMBER와 동일한 기능을 이용할 수 있도록 조치

        } else if(memberEntity.getAuthLevel() == 3){

            // authLevel이 3 일때 일반 회원으로 권한 부여
            authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));

        }else if(memberEntity.getAuthLevel() == 4){

            // authLevel이 4 일때 블록(차단)회원으로 권한 부여(permitAll로 설정된 홈이나 게시물리스트페이지만 이용 가능)
            authorities.add(new SimpleGrantedAuthority(Role.BLOCK.getValue()));
        }

        // spring security에서 제공하는 UserDetails를 구현한 User를 반환(org.springframework.security.core.userdetails.User )
        // 원래 반환하는 User(UserDetails를 상속하는 클래스?) 정보는 로그인아이디, 로그인비밀번호, 권한리스트이다.
        // UserDetails를 커스텀함. 로그인한 회원의 회원번호, 로그인아이디, 이름, 닉네임, 이메일, 권한을 담는다.
        return new CustomUserDetails(memberEntity.getId(), memberEntity.getLoginId(), memberEntity.getLoginPw(),
                memberEntity.getName(), memberEntity.getNickname(), memberEntity.getEmail(), memberEntity.getAuthKey(), authorities);
    }


    // 비밀번호 찾기 임시 구현용입니다.
    // 임시비밀번호 받아서 메일로 보내기기
    @Transactional
   public void findLoginPw(String loginId, String email) {
        Util util = new Util();

        Member member = findByLoginId(loginId);

        if( !member.getEmail().equals(email) ){
            throw new IllegalStateException("존재하지 않은 회원입니다.");
        }

        String newPw = util.getRandomPw(8);  // 8자리 임시 비밀번호 생성

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        mailService.sendMail(email, "pptor 임시 비밀번호",
                "임시비밀번호는 " + newPw + " 입니다. 로그인 후 변경해주세요.");

        member.changeMemberInfo(passwordEncoder.encode(newPw), member.getNickname(), member.getEmail(), member.getAuthLevel());

        memberRepository.modify(member);

    }

    // 로그인 상태에서 이메일 인증을 확인하는 메서드
    @Transactional
    public boolean checkAuth(CustomUserDetails user, String authKey) {  // join 때 DB에 저장된 authKey와 이메일로 보낸 authKey를 비교
        if( user.getAuthKey().equals(authKey) == false ){
            // 틀리면 false 리턴
            return false;
        }

        Member member = findByLoginId(user.getUsername());

        // 일치하면 member의 authLevel을 3으로 변경하고 true 리턴
        member.changeMemberInfo(member.getLoginPw(), member.getNickname(), member.getEmail(), 3);
        memberRepository.modify(member);

        // 로그인한 회원이 인증되면 MEMBER 권한을 넣어준다.(다시 로그인 하지 않아도 됨)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> updatedAuthorities = new ArrayList<>(authentication.getAuthorities());
        updatedAuthorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
        Authentication newAuth = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), updatedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        return true;
    }

    /*
   회원 전부를 불러오기
    */
    public List<Member> findAllMember(){
        return memberRepository.findAll();
    }

    /*
    회원 수를 카운트하기
     */
    public Long count() {
        return memberRepository.count();
    }


}


