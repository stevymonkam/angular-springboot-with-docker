package com.stevy.contratti.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    public JavaMailSender emailSender;

    @Override
    public void sendSimpleMessage(String[] to,String[] cc, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setCc(cc);
            message.setSubject(subject);
            message.setText(text);

            emailSender.send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void sendMessageWithAttachment(String[] to,String[] cc, String subject, String text, String pathToAttachment) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            // pass 'true' to the constructor to create a multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true ,"UTF-8");
            helper.setTo(to);
            helper.setCc(cc);
            helper.setSubject(subject);
            helper.setText(text);
            FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
            helper.addAttachment(pathToAttachment.substring(8), file);
            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

