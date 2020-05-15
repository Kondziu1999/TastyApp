package com.kondziu.projects.TastyAppBackend.services;

import com.kondziu.projects.TastyAppBackend.models.ConfirmationToken;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class PasswordRestoreService {

    private final EmailSenderServiceImpl emailSenderService;

    public PasswordRestoreService(EmailSenderServiceImpl emailSenderService){
        this.emailSenderService=emailSenderService;
    }

    public void sendRestorePasswordEmail(String token,String email,String redirectUrl){
        SimpleMailMessage message=prepareRestorePasswordEmail(token,email,redirectUrl);
        this.emailSenderService.sendMail(message);
    }

    //redirectUrl is url to frontend par of app
    private SimpleMailMessage prepareRestorePasswordEmail(String token, String email,String redirectUrl){
        SimpleMailMessage mailMessage= new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("konrad.podgorski1999@gmail.com");
        mailMessage.setText("To reset your password, please click here : "
                +redirectUrl+"?token="+token);
        return mailMessage;

    }

}
