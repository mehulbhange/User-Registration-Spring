package com.bridgelabz.userregistrationapp.service;

public interface EmailService {
    // method to send a simple email
    void sendSimpleMail(String recipient, String subject, String body);
}
