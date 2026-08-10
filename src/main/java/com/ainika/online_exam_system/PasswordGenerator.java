package com.ainika.online_exam_system;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String hash = encoder.encode("student123");

        System.out.println(hash);
        System.out.println(encoder.matches("student123", hash));
    }
}
