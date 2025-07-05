package com.ecommerce_api.demo.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ecommerce_api.demo.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final MyUserDetailsService userDetailsService;
    private final JWTUtil jwtUtil;
    private final TokenService tokenService;

    @Autowired
    public JWTFilter(MyUserDetailsService userDetailsService,
                     JWTUtil jwtUtil,
                     TokenService tokenService) {

        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);

            try {
                if(tokenService.isBlacklisted(jwt)){
                    throw new JWTVerificationException("Token is blacklisted");
                }

                if (!jwtUtil.isTokenValid(jwt)) {
                    throw new JWTVerificationException("Token is expired or invalid");
                }

                String email = jwtUtil.extractEmail(jwt);
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());

                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }

            } catch (JWTVerificationException ex) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or Expired JWT Token");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
