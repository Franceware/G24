package com.g24.authentication.service;

import com.g24.authentication.entity.Mail;

public interface EmailService 
{
    void sendEmail(Mail mail) throws RuntimeException;
}
