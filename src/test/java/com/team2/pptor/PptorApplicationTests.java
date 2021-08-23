package com.team2.pptor;

import com.team2.pptor.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PptorApplicationTests {

	@Autowired
	private MemberService memberService;

	@Test
	void contextLoads() {
	}

	@Test
	void findMemberByLoginId(){
//		MemberForm memberForm = new MemberForm();
//		memberForm.setLoginId("aa");
//		memberForm.setLoginPw("aa");
//		memberForm.setName("aa");
//		memberForm.setNickName("aa");
//		memberForm.setEmail("aa");
//
//		Member member = Member.createMember(memberForm.getLoginId(),
//				memberForm.getLoginPw(),
//				memberForm.getName(),
//				memberForm.getNickName(),
//				memberForm.getEmail());
//
//		memberService.join(member);


		//Optional<Member> checkedMember = memberService.checkMember("aa", "aa");

		//Assertions.assertThat(member.getLoginId()).isEqualTo(checkedMember.get().getLoginId());
	}
}
