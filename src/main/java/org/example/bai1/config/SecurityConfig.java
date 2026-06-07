package org.example.bai1.config;

import lombok.RequiredArgsConstructor;
import org.example.bai1.service.UserDetailServiceCustom;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailServiceCustom userDetailServiceCustom;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // dùng cho authenticate()
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // tắt csrf
                .csrf(csrf -> csrf.disable())
                // không dùng session
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // phân quyền request
                .authorizeHttpRequests(auth -> auth
                        // public api
                        .requestMatchers("/api/auth/**").permitAll()
                        // ADMIN
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        // STAFF
                        .requestMatchers("/api/staff/**").hasAnyRole("ADMIN", "STAFF")
                        // authenticated
                        .anyRequest().authenticated())

                // basic config
                .httpBasic(Customizer.withDefaults())

                // gắn userDetailsService
                .userDetailsService(userDetailServiceCustom);

        return http.build();
    }
}