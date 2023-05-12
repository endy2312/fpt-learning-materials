package com.fpt.g2.service;

import com.fpt.g2.entity.EmailDetails;

public interface EmailService {
     String sendSimpleMail(String recipient,String type);
     String sendSimpleMailWithPassword(String recipient);

    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EmailDetails details);
}
