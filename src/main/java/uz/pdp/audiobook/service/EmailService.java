package uz.pdp.audiobook.service;

public interface EmailService {

    void sendEmail(String to, String code, String subject, String text);

}