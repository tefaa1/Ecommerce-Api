package com.ecommerce_api.demo.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JWTFilter filter;
    private final MyUserDetailsService uds;

    @Autowired
    public SecurityConfig(JWTFilter filter, MyUserDetailsService uds) {
        this.filter = filter;
        this.uds = uds;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(CsrfConfigurer::disable)
                .httpBasic(HttpBasicConfigurer::disable)
                .cors(cors -> {})
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**","/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        .requestMatchers("/api/cart/admin/all").hasRole("ADMIN")
                        .requestMatchers("/api/cart/**").authenticated()

                        .requestMatchers(HttpMethod.GET,"/api/category/**").authenticated()
                        .requestMatchers("/api/category/**").hasRole("ADMIN")

                        .requestMatchers("/api/order/admin/all").hasRole("ADMIN")
                        .requestMatchers("/api/order/**").authenticated()

                        .requestMatchers(HttpMethod.GET,"/api/product/**").authenticated()
                        .requestMatchers("/api/product/**").hasRole("ADMIN")

                        .requestMatchers("/api/review/admin/all").hasRole("ADMIN")
                        .requestMatchers("/api/review/**").authenticated()

                        .requestMatchers("/api/user/admin/all").hasRole("ADMIN")
                        .requestMatchers("/api/user/**").authenticated()

                        .requestMatchers("/api/wishlist/**").authenticated()

                        .anyRequest().authenticated()
                )
                .userDetailsService(uds)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(
                                (request, response, authException) ->
                                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
                        )
                )
                .sessionManagement(sess -> sess
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
