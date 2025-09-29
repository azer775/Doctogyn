package org.example.user.Configurations;

import lombok.RequiredArgsConstructor;
import org.example.user.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity  // Enables web security
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    private final JwtFilter jwtAuthFilter;  // Custom JWT filter
    private final AuthenticationProvider authenticationProvider;  // Provider from BeansConfig

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())  // Enable CORS from BeansConfig
                .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF for stateless API
                .authorizeHttpRequests(req ->
                        req.requestMatchers(
                                        "/auth/**",  // Public auth endpoints
                                        "/auth/validate",  // New: Token validation for other services
                                        "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html"  // Swagger public
                                )
                                .permitAll()  // Allow without auth
                                .anyRequest().authenticated()  // All others require auth
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Stateless, no sessions
                .authenticationProvider(authenticationProvider)  // Use custom provider
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);  // Add JWT filter before default

        return http.build();
    }
}
