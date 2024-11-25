package com.xu.service;

import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    public void sendOverdueReminder(String to, String subject, String text) throws MessagingException;
}
