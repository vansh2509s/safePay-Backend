package com.self.SafePay.config;

import com.self.SafePay.Security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
            throws Exception {

        httpSecurity
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        // Public APIs
                        .requestMatchers(HttpMethod.POST,
                                "/auth/register",
                                "/auth/login"
                        ).permitAll()
                        //Qr APIs
                        .requestMatchers("/api/qr/**").authenticated()
                        // Wallet APIs
                        .requestMatchers("/wallet/**").authenticated()

                        // Bank APIs
                        .requestMatchers("/bank/**").authenticated()

                        // Beneficiary APIs
                        .requestMatchers("/beneficiary/**").authenticated()

                        // Transaction APIs
                        .requestMatchers("/transaction/**").authenticated()

                        // Everything else
                        .anyRequest().authenticated()
                )

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return httpSecurity.build();
    }
}