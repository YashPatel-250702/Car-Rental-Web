package com.tekworks.rental.filter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tekworks.rental.service.JWTService;
import com.tekworks.rental.service.MyUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private MyUserDetailsService detailsService;

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        String token = null;
        String username = null;
        String role = null;

        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
            logger.info("JWT Token extracted: {}", token);
            username = jwtService.extractUserName(token);
            role = jwtService.extractRoles(token); // Extract role from token
            logger.info(" Username: {}", username);
            logger.info(" Role: {}", role);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails details = detailsService.loadUserByUsername(username);
            Collection<? extends GrantedAuthority> authorities = details.getAuthorities();

            // Convert authorities to a readable format
            String assignedRoles = authorities.stream()
                                              .map(GrantedAuthority::getAuthority)
                                              .collect(Collectors.joining(", "));
            logger.info("UserDetails: {}", details.getUsername());
            logger.info("User roles: {}", assignedRoles);

            if (jwtService.isTokenValid(token, details)) {            
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        details, null, authorities);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } 
        } 

        filterChain.doFilter(request, response);
    }
}
