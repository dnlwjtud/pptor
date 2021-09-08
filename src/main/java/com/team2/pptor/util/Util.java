package com.team2.pptor.util;

import com.team2.pptor.domain.Article.Content;

import java.security.SecureRandom;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util {

    // 임시비밀번호 생성(SecureRandom 사용)
    public String getRandomPw(int pwLength){
        SecureRandom secureRandom = new SecureRandom();

        String english_lower = "abcdefghijklmnopqrstuvwxyz";
        String english_upper = english_lower.toUpperCase();
        String numbers = "0123456789";

        String stringDatas = english_lower + english_upper + numbers;

        StringBuilder stringBuilder = new StringBuilder(pwLength);

        // charAt(index) 은 index 위치의 문자를 불러온다.
        // String testStr = "가나다라마" 에서 testStr.charAt(2)는 인덱스 2 위치의 '다'를 불러온다.
        for(int i = 0; i < pwLength; i++) {
            stringBuilder.append(stringDatas.charAt(secureRandom.nextInt(stringDatas.length())));
        }
        return stringBuilder.toString();
    }

}
