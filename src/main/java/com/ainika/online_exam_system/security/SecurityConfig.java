package com.ainika.online_exam_system.security;
import org.springframework.http.HttpMethod;
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


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


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


    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http)
            throws Exception {

        http
                .cors(cors -> cors.configurationSource(
                        corsConfigurationSource()
                ))

                .csrf(csrf -> csrf.disable())


                .authorizeHttpRequests(auth -> auth


                        // =========================
                        // PUBLIC
                        // =========================

                        .requestMatchers("/auth/login")
                        .permitAll()

                        .requestMatchers("/users/**")
                        .permitAll()

                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        )
                        .permitAll()


                        // =========================
                        // EXAMS
                        // =========================

                        .requestMatchers("/exams/**")
                        .hasAnyRole(
                                "TEACHER",
                                "STUDENT"
                        )


                        // =========================
                        // QUESTIONS
                        // =========================

                        .requestMatchers("/questions/**")
                        .hasRole("TEACHER")


                        // =========================
                        // STUDENT RESPONSES
                        // =========================

                        .requestMatchers("/responses/**")
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
                        // Used by Teacher Dashboard / Results
                        .requestMatchers(
                                HttpMethod.GET,
                                "/attempts"
                        )
                        .hasRole("TEACHER")


                        // Specific attempt
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


                .httpBasic(
                        Customizer.withDefaults()
                );


        http.addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
        );


        return http.build();
    }
}

