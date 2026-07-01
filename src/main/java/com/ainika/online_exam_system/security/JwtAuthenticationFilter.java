package com.ainika.online_exam_system.security;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import com.ainika.online_exam_system.service.CustomUserDetailsService;
import com.ainika.online_exam_system.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();

//        if (path.equals("/auth/login")
//                || path.startsWith("/users")) {
//
//            filterChain.doFilter(request, response);
//            return;
//        }

        if (path.equals("/auth/login")
                || path.startsWith("/users")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")) {

            filterChain.doFilter(request, response);
            return;
        }

        System.out.println("JWT Filter Executed");

        String authHeader =
                request.getHeader("Authorization");

        if (authHeader != null &&
                authHeader.startsWith("Bearer ")) {

            String token =
                    authHeader.substring(7);

            try {

                String email =
                        jwtService.extractEmail(token);

                UserDetails userDetails =
                        customUserDetailsService.loadUserByUsername(email);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);

                System.out.println(
                        "Authenticated User: " + email
                );

            }

//             catch (Exception e) {
//
//                response.setStatus(
//                        HttpServletResponse.SC_UNAUTHORIZED
//                );
//
//                return;
//            }
            catch (Exception e) {

                e.printStackTrace();

                response.setStatus(
                        HttpServletResponse.SC_UNAUTHORIZED
                );

                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
