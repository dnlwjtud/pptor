package com.team2.pptor.service;

import com.team2.pptor.domain.Member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class TestService implements CommandLineRunner, ApplicationListener<ContextClosedEvent>, InitializingBean, DisposableBean {

    private final MemberService memberService;
    private final ArticleService articleService;
    private final BoardService boardService;


    @PostConstruct
    private void init() {
        System.err.println("PostConstruct annotation으로 빈이 완전히 생성된 후에 한 번 수행될 메서드에 붙입니다.");
    }
    @Override
    public void run(String... args) throws Exception {
        System.err.println("commandLineRunner 인터페이스 구현 메서드입니다. '애플리케이션'이 실행될 때 '한 번' 실행됩니다.");
//        memberService.makeTestData();
//
//        // 게시글 테스트
//        // 게시글 작성한 멤버는 super로 고정(이메일 인증한 멤버).
//        Member testMember = memberService.findByLoginId("super");
//
//
//        // 게시판 테스트
//        boardService.makeTestData();
//
//        articleService.makeTestData(testMember);
//
//
//
//        System.err.println("Test데이터 생성완료");
    }
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.err.println("ApplicationListener<ContextClosedEvent> 인터페이스 구현 메서드 입니다. '애플리케이션'이 죽었을 때 '한 번' 실행됩니다.");
        System.err.println("이벤트 발생 시간(timestamp) : " + event.getTimestamp());

        goLogout();
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        System.err.println("InitializingBean 인터페이스 구현 메서드입니다. TestService 'Bean'이 생성될 때 마다 호출되는 메서드 입니다.");
    }
    @Override
    public void destroy() throws Exception {

        System.err.println("DisposableBean 인터페이스 구현 메서드입니다. TestService 'Bean'이 소멸될 때 마다 호출되는 메서드입니다");
    }

    public String goLogout(){
        return "redirect:/members/logout";
    }
}
