package com.team2.pptor.mail;


import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailService {

    private JavaMailSender mailSender;
    private static final String fromAddress = "pptor";

    public void sendMail(String address, String title, String body){

        try {
            MailHandler mailHandler = new MailHandler(mailSender);

            mailHandler.setFrom(MailService.fromAddress);  // 보내는 사람 주소
            mailHandler.setTo(address);  // 받는 사람 주소
            mailHandler.setSubject(title); // 제목

            // 내용(html)
            String htmlContent = "<p>" + body +"<p>";
            mailHandler.setText(htmlContent, true);

            // 첨부 파일
            mailHandler.setAttach("newTest.txt", "static/originTest.txt");
            // 이미지 삽입
            mailHandler.setInline("sample-img", "static/sample1.jpg");

            mailHandler.send();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void sendMailWithImg(String address, String title, String body){
        try {
            MailHandler mailHandler = new MailHandler(mailSender);

            mailHandler.setFrom(MailService.fromAddress);  // 보내는 사람 주소
            mailHandler.setTo(address);  // 받는 사람 주소
            mailHandler.setSubject(title); // 제목

            // 내용(html)
            String htmlContent = "<p>" + body +"<p>";
            mailHandler.setText(htmlContent, true);

            // 이미지 삽입
            mailHandler.setInline("sampleImg", "static/img/logo-b.png");

            mailHandler.send();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

//    public void sendMail(String address, String title, String body){
//
//        SimpleMailMessage message = new SimpleMailMessage();  // 텍스트만 보낼 수 있습니다.
//        message.setFrom(fromAddress); // 보내는사람 입력, 현재 작동안됨
//        message.setTo(address); // 수신자 입력
//        message.setSubject(title); // 제목입력
//        message.setText(body); // 내용입력
//
//        mailSender.send(message);
//    }



}
