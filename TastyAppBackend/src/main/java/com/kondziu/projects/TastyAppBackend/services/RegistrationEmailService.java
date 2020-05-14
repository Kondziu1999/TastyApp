package com.kondziu.projects.TastyAppBackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class RegistrationEmailService {

    private EmailSenderService emailSenderService;

    @Value("${app.domain}")
    private String DOMAIN;

    @Autowired
    public RegistrationEmailService(EmailSenderServiceImpl emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    public void prepareMessageAndSend(String token,String email){
        emailSenderService.sendMail(prepareMessage(token,email));
    }

    private SimpleMailMessage prepareMessage(String token,String email){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("konrad.podgorski1999@gmail.com");
        mailMessage.setText("To confirm your account, please click here : "
                +DOMAIN+"/api/auth/confirmEmail/"+token);
        return mailMessage;
    }

}
