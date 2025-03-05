package uz.pdp.audiobook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AudioBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(AudioBookApplication.class, args);
    }

}
