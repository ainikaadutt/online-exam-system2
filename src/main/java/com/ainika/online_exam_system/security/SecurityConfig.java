package com.ainika.online_exam_system.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;


    // =========================
    // PASSWORD ENCODER
    // =========================

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // =========================
    // CORS
    // =========================

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of("http://localhost:3000")
        );

        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "OPTIONS"
                )
        );

        configuration.setAllowedHeaders(
                List.of("*")
        );

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }


    // =========================
    // SECURITY FILTER CHAIN
    // =========================

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http)
            throws Exception {

        http

                // =========================
                // CORS + CSRF
                // =========================

                .cors(cors ->
                        cors.configurationSource(
                                corsConfigurationSource()
                        )
                )

                .csrf(csrf ->
                        csrf.disable()
                )


                // =========================
                // AUTHORIZATION
                // =========================

                .authorizeHttpRequests(auth -> auth

                        // =========================
                        // PUBLIC ENDPOINTS
                        // =========================

                        .requestMatchers(
                                "/auth/login"
                        )
                        .permitAll()

                        .requestMatchers(
                                "/users/**"
                        )
                        .permitAll()

                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        )
                        .permitAll()


                        // =========================
                        // EXAMS
                        // =========================

                        // Students and teachers can view exams
                        .requestMatchers(
                                HttpMethod.GET,
                                "/exams/**"
                        )
                        .hasAnyRole(
                                "TEACHER",
                                "STUDENT"
                        )

                        // Only teachers can create exams
                        .requestMatchers(
                                HttpMethod.POST,
                                "/exams"
                        )
                        .hasRole("TEACHER")

                        // Only teachers can update exams
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/exams/**"
                        )
                        .hasRole("TEACHER")

                        // Only teachers can delete exams
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/exams/**"
                        )
                        .hasRole("TEACHER")


                        // =========================
                        // QUESTIONS
                        // =========================

                        // Students need to read questions
                        // while attempting an exam.
                        //
                        // Teachers also need to read questions
                        // for question management.

                        .requestMatchers(
                                HttpMethod.GET,
                                "/questions/**"
                        )
                        .hasAnyRole(
                                "TEACHER",
                                "STUDENT"
                        )

                        // Only teachers can create questions
                        .requestMatchers(
                                HttpMethod.POST,
                                "/questions"
                        )
                        .hasRole("TEACHER")

                        // Only teachers can update questions
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/questions/**"
                        )
                        .hasRole("TEACHER")

                        // Only teachers can delete questions
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/questions/**"
                        )
                        .hasRole("TEACHER")


                        // =========================
                        // STUDENT RESPONSES
                        // =========================

                        .requestMatchers(
                                "/responses/**"
                        )
                        .hasRole("STUDENT")


                        // =========================
                        // EXAM ATTEMPTS
                        // =========================

                        // Student submits an exam
                        .requestMatchers(
                                HttpMethod.POST,
                                "/attempts/submit"
                        )
                        .hasRole("STUDENT")

                        // Student views their own attempts
                        .requestMatchers(
                                HttpMethod.GET,
                                "/attempts/student"
                        )
                        .hasRole("STUDENT")

                        // Teacher views all attempts
                        .requestMatchers(
                                HttpMethod.GET,
                                "/attempts"
                        )
                        .hasRole("TEACHER")

                        // Specific attempt
                        // Both student and teacher can view
                        .requestMatchers(
                                HttpMethod.GET,
                                "/attempts/*"
                        )
                        .hasAnyRole(
                                "TEACHER",
                                "STUDENT"
                        )


                        // =========================
                        // EVERYTHING ELSE
                        // =========================

                        .anyRequest()
                        .authenticated()
                )


                // =========================
                // HTTP BASIC
                // =========================

                .httpBasic(
                        Customizer.withDefaults()
                );


        // =========================
        // JWT FILTER
        // =========================

        http.addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
        );


        return http.build();
    }
}