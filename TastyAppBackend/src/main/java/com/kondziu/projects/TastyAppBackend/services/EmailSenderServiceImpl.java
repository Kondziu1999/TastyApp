package com.kondziu.projects.TastyAppBackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Service
@EnableAsync
public class EmailSenderServiceImpl {

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailSenderServiceImpl(JavaMailSender javaMailSender){
        this.javaMailSender=javaMailSender;
    }

    @Async
    public void sendMail(SimpleMailMessage message) {
        System.out.println("SEND EMAIL !!!!!!!!!!!!!!!!!!!!!!");
        javaMailSender.send(message);
    }
}
