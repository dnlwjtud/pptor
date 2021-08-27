package com.team2.pptor.mail;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;

public class MailHandler {

    private JavaMailSender sender;
    private MimeMessage message;
    private MimeMessageHelper messageHelper;

    public MailHandler(JavaMailSender mailSender) throws MessagingException {
        this.sender = mailSender;
        message = mailSender.createMimeMessage();
        messageHelper = new MimeMessageHelper(message, true, "UTF-8");
        // 단순한 텍스트 메시지만 보낼때
        // messageHelper = new MimeMessageHelper(mail,"UTF-8");
    }

    // 보내는 사람(이메일)
    public void setFrom(String fromAddress) throws MessagingException {
        messageHelper.setFrom(fromAddress);
    }

    // 받는 사람(이메일)
    public void setTo(String email) throws MessagingException {
        messageHelper.setTo(email);
    }

    // 제목
    public void setSubject(String subject) throws MessagingException {
        messageHelper.setSubject(subject);
    }

    // 내용
    public void setText(String text, boolean useHtml) throws MessagingException {
        messageHelper.setText(text, useHtml);
    }

    // 파일 첨부
    public void setAttach(String fileName, String filePath) throws MessagingException, IOException {
        File file = new ClassPathResource(filePath).getFile(); // 파일경로에서 파일 가져오기
        FileSystemResource fsr = new FileSystemResource(file);

        messageHelper.addAttachment(fileName, fsr);
    }

    // 이미지 삽입하기
    public void setInline(String contentId, String filePath) throws MessagingException, IOException {
        File file = new ClassPathResource(filePath).getFile();
        FileSystemResource fsr = new FileSystemResource(file);

        messageHelper.addInline(contentId, fsr);
    }

    // 발송하기
    public void send() {
        try {
            sender.send(message);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
