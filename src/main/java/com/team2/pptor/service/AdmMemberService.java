package com.team2.pptor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdmMemberService {


    public String status(String userStatus) {

        if(userStatus.equals("[ROLE_MEMBER]")){
            return "일반 회원";
        }
        
        if(userStatus.equals("[ROLE_ADMIN]")){
            return "관리자";
        }

        if(userStatus.equals("[ROLE_PREMEMBER]")){
            return "준회원";
        }

        return "회원가입이 필요합니다.";
    }
}
