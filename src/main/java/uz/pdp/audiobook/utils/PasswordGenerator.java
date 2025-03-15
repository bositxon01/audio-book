package uz.pdp.audiobook.utils;

import java.security.SecureRandom;

public class PasswordGenerator {

    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "@#$%^&+=!";
    private static final String ALL_CHARACTERS = UPPERCASE + LOWERCASE + DIGITS + SPECIAL_CHARACTERS;
    private static final int PASSWORD_LENGTH = 10; // At least 8, but safer with 10+

    private static final SecureRandom random = new SecureRandom();

    public static String generateRandomPassword() {
        StringBuilder password = new StringBuilder();

        password.append(getRandomChar(UPPERCASE));
        password.append(getRandomChar(DIGITS));
        password.append(getRandomChar(SPECIAL_CHARACTERS));

        for (int i = 3; i < PASSWORD_LENGTH; i++) {
            password.append(getRandomChar(ALL_CHARACTERS));
        }

        return shuffleString(password.toString());
    }

    private static char getRandomChar(String chars) {
        return chars.charAt(random.nextInt(chars.length()));
    }

    private static String shuffleString(String input) {
        char[] characters = input.toCharArray();

        for (int i = characters.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = characters[i];
            characters[i] = characters[j];
            characters[j] = temp;
        }

        return new String(characters);
    }
}
