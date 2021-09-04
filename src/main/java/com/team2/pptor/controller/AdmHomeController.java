package com.team2.pptor.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdmHomeController {

    /*
    관리자 홈
    */
    @GetMapping("/adm")
    public String showHome(){

        return "adm/home";
    }


}
