package com.team2.pptor.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {

    /*
    JSON 형태의 String 을 객체화 하기위한 메소드
     */
    public static <T> T getObjFromJsonString (String json, Class<T> cls) {

        // Jackson ObjectMapper 객체 생성
        ObjectMapper om = new ObjectMapper();

        try {
            // readValue(json, class)
            return (T) om.readValue(json, cls);

        } catch (JsonProcessingException e ) {
            // 변환에 실패하였을 경우 exception throw, null리턴
            e.printStackTrace();
            return null;

        }

    }

    /*
    객체를 JSON 형태의 String 으로 변환하는 메소드
     */
    public static String getJsonFromObj(Object obj) {

        // ObjectMapper 객체 생성
        ObjectMapper om = new ObjectMapper();

        // 예외처리
        try {
            // 객체를 String 으로 변환하여 리턴
            return om.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // 변경실패시 공백 리턴
        return "";

    }

}
