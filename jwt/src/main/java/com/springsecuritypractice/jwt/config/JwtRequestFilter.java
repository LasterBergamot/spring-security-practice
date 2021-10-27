package com.springsecuritypractice.jwt.config;

import com.springsecuritypractice.jwt.service.JwtUserDetailsService;
import com.springsecuritypractice.jwt.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil tokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        AuthenticationContext context = getDataFromHeader(request);
        setAuthentication(request, context);
        filterChain.doFilter(request, response);
    }

    private AuthenticationContext getDataFromHeader(HttpServletRequest request) {
        final String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);

            try {
                username = tokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException exception) {
                log.error("Unable to get JWT Token");
            } catch (ExpiredJwtException exception) {
                log.error("JWT token has expired");
            }
        } else {
            log.warn("JWT Token does not begin with Bearer String");
        }

        return new AuthenticationContext(jwtToken, username);
    }

    private void setAuthentication(HttpServletRequest request, AuthenticationContext context) {
        String username = context.getUsername();

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (tokenUtil.validateToken(context.getJwtToken(), userDetails)) {
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(token);
            }
        }
    }

    @AllArgsConstructor
    @Getter
    private static class AuthenticationContext {
        private String jwtToken;
        private String username;
    }
}
