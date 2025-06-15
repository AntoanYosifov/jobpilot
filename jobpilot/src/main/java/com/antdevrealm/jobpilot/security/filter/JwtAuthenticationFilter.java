package com.antdevrealm.jobpilot.security.filter;

import com.antdevrealm.jobpilot.security.service.JobPilotUserDetailsService;
import com.antdevrealm.jobpilot.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final List<RequestMatcher> EXCLUDED_PATHS = List.of(
            new AntPathRequestMatcher("/api/users/register"),
            new AntPathRequestMatcher("/api/users/login"),
            new AntPathRequestMatcher("/api/jobs")
    );

    private final JwtUtil jwtUtil;
    private final JobPilotUserDetailsService jobPilotUserDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, JobPilotUserDetailsService jobPilotUserDetailsService) {
        this.jwtUtil = jwtUtil;
        this.jobPilotUserDetailsService = jobPilotUserDetailsService;
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        return EXCLUDED_PATHS.stream()
                .anyMatch(matcher -> matcher.matches(request));
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        if(shouldNotFilter(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = getTokenFromRequest(request);

        if(token == null) {
            throw new BadCredentialsException("Missing or malformed Authorization header");
        }

        try {
            String username = jwtUtil.extractUsername(token);

            if(!jwtUtil.validateToken(token, username)) {
                throw new BadCredentialsException("Token validation failed");
            }

            UserDetails userDetails = jobPilotUserDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authToken);

        } catch (JwtException | IllegalArgumentException ex) {
            throw new BadCredentialsException("Invalid or expired token", ex);
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
