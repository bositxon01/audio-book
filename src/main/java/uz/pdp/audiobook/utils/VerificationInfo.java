package uz.pdp.audiobook.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import uz.pdp.audiobook.entity.User;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Component
public class VerificationInfo {

    private String code;

    private User user;

    private long expiryTime;

    private int attempts;

}
