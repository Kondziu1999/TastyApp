package com.kondziu.projects.TastyAppBackend.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.EnableAsync;

public interface EmailSenderService {

    void sendMail(SimpleMailMessage message);
}
