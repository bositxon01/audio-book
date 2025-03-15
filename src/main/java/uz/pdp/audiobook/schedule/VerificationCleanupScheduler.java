package uz.pdp.audiobook.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uz.pdp.audiobook.utils.VerificationInfo;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class VerificationCleanupScheduler {
    private final Map<String, VerificationInfo> verificationData;

    @Scheduled(fixedRate = 60_000)
    public void cleanUpExpiredVerifications() {
        long now = System.currentTimeMillis();

        verificationData.entrySet()
                .removeIf(entry ->
                        (now > entry.getValue().getExpiryTime()));
    }

}
