package uz.pdp.audiobook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uz.pdp.audiobook.utils.VerificationInfo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class VerificationConfig {

    @Bean
    public Map<String, VerificationInfo> verificationData() {
        return new ConcurrentHashMap<>();
    }

}
