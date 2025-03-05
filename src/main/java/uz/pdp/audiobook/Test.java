package uz.pdp.audiobook;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Test {
    private final JavaMailSender mailSender;

    public static void main(String[] args) {
        Test test = new Test(new JavaMailSenderImpl());
        test.sendTestEmail();
    }

    public void sendTestEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("croni075@gmail.com");
        message.setTo("turkiyauchun1@gmail.com");
        message.setSubject("Test Email from Spring Boot");
        message.setText("Hello! This is a test email from Spring Boot.");
        message.setReplyTo("turkiyauchun1@gmail.com");

        try {
            mailSender.send(message);
            System.out.println("✅ Test email sent successfully!");
        } catch (Exception e) {
            System.err.println("❌ Error sending email: " + e.getMessage());
        }
    }
}
