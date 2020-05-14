package com.kondziu.projects.TastyAppBackend.services;

import org.springframework.mail.SimpleMailMessage;

public interface EmailSenderService {

    void sendMail(SimpleMailMessage message);
}
