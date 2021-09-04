package com.team2.pptor.controller;

import com.team2.pptor.domain.Member.Member;
import com.team2.pptor.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdmMemberController {

    private final MemberService memberService;

    @GetMapping("adm/member/manage")
    public String showMemberManage(Model model){

        List<Member> members = memberService.findAllMember();

        model.addAttribute("members", members);
        model.addAttribute("count", memberService.count());

        return "adm/member/manage";
    }


}
