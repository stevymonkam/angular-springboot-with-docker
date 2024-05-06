package com.stevy.contratti.service.email;


public interface EmailService {
    void sendSimpleMessage(String[] to,String[] cc, String subject, String text);
    void sendMessageWithAttachment(String[] to,String[] cc,String subject, String text, String pathToAttachment);
}
