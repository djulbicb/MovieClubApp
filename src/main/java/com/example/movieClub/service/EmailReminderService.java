package com.example.movieClub.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class EmailReminderService {

    private final JavaMailSender javaMailSender;

    @RabbitListener(queues = "emailQueue")
    public void sendReminderEmails(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("adore.nails.ns@gmail.com");
        message.setTo(email);
        message.setSubject("Please return overdue movie rental");
        message.setText("Your movie rental is overdue, please return it at your earliest convenience.");

        javaMailSender.send(message);
        log.info("Email successfully sent.");
    }
}
