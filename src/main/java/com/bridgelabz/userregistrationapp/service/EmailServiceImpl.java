package com.bridgelabz.userregistrationapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final String sender = "mehulbhange1806@gmail.com";

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendSimpleMail(String recipient, String subject, String body) {
        try {

            // Creating a simple mail message
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(recipient);
            mailMessage.setText(body);
            mailMessage.setSubject(subject);

            // Sending the mail
            javaMailSender.send(mailMessage);
        }
        // Catch block to handle the exceptions
        catch (Exception e) {
        }
    }
}
