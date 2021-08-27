package com.team2.pptor.mail;

import lombok.Getter;

@Getter
public class Mail {
    private String address;
    private String title;
    private String body;

    public Mail(String address, String title, String body){
        this.address = address;
        this.title = title;
        this.body = body;
    }
}
