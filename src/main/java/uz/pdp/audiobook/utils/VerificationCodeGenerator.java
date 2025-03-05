package uz.pdp.audiobook.utils;

import java.util.Random;

public class VerificationCodeGenerator {

    public static String generateVerificationCode() {
        return String.valueOf(new Random().nextInt(100_000, 999_999));
    }
}
