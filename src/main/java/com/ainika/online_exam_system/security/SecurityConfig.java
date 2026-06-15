package com.ainika.online_exam_system.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth

                        .requestMatchers("/auth/login")
                        .permitAll()

                        .requestMatchers("/users/**")
                        .permitAll()

                        .requestMatchers("/exams/**")
                        .hasRole("TEACHER")

                        .requestMatchers("/questions/**")
                        .hasRole("TEACHER")

                        .requestMatchers("/responses/**")
                        .hasRole("STUDENT")

                        .requestMatchers("/attempts/**")
                        .hasRole("STUDENT")

                        .anyRequest()
                        .authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        http.addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }


}
