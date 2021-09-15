package com.team2.pptor.controller;

import com.team2.pptor.domain.Member.Member;
import com.team2.pptor.domain.Member.MemberSaveForm;
import com.team2.pptor.domain.Member.MemberSearchForm;
import com.team2.pptor.security.CustomUserDetails;
import com.team2.pptor.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/adm/manage")
public class AdmMemberController {

    private final MemberService memberService;

    @GetMapping("/members")
    public String showMemberManage(Model model, @AuthenticationPrincipal CustomUserDetails user,
                                   @PageableDefault(size = 6, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                   @RequestParam(value="searchType", defaultValue = "") String searchType,
                                   @RequestParam(value="searchKeyword", defaultValue = "") String searchKeyword) {

        // admin권한이 아니면 페이지 접속 불가
        if ( !user.getAuthorities().toString().contains("ROLE_ADMIN") )  {
            return "redirect:/";
        }

        List<Member> members = memberService.findAllMember();
        Page<Member> membersPage = memberService.getSearchedMemberPage(pageable, searchType, searchKeyword);



        Long count = memberService.count();

        model.addAttribute("members", members);
        model.addAttribute("count", count);
        model.addAttribute("membersPage", membersPage);
        model.addAttribute("totalPage", membersPage.getTotalPages());

        return "adm/member/manage";
    }

    @PutMapping("/members/{loginId}")
    @ResponseBody
    public String blockMember(@PathVariable(name = "loginId") String loginId,
                              @AuthenticationPrincipal CustomUserDetails user){
        if ( !user.getAuthorities().toString().contains("ROLE_ADMIN") )  {
            return "redirect:/";
        }


        Member member = memberService.findByLoginId(loginId);

        if(member.getAuthLevel() == 4){
            member.changeMemberInfo(member.getLoginPw(), member.getNickname(), member.getEmail(), 3);

            memberService.modifyInfo(member);

            return "adm/member/manage";
        }

        member.changeMemberInfo(member.getLoginPw(), member.getNickname(), member.getEmail(), 4);

        memberService.modifyInfo(member);

        return "adm/member/manage";
    }

    @DeleteMapping("/members/{loginId}")
    @ResponseBody
    public String deleteMember(@PathVariable(name = "loginId") String loginId, @AuthenticationPrincipal CustomUserDetails user){
        if ( !user.getAuthorities().toString().contains("ROLE_ADMIN") )  {
            System.out.println("관리자가 아닙니다.");
            return "redirect:/";
        }

        memberService.delete(loginId);

        return "adm/member/manage";
    }



}
