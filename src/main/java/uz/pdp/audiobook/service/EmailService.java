package uz.pdp.audiobook.service;

public interface EmailService {

    void sendVerificationEmail(String to, String code);

}