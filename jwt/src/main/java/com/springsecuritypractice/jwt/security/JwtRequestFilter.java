package com.springsecuritypractice.jwt.security;

import com.springsecuritypractice.jwt.service.JwtUserDetailsService;
import com.springsecuritypractice.jwt.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final List<String> POST_URIS = List.of("/authenticate", "/register");

    private static final String BEARER_PREFIX = "Bearer ";

    private static final String ERR_MSG_TOKEN_EXPIRED = "JWT token has expired";
    private static final String ERR_MSG_INVALID_TOKEN = "Invalid JWT token";

    private static final String WRN_MSG_INVALID_PREFIX = "JWT token does not begin with Bearer String";

    private final JwtUserDetailsService userDetailsService;

    private final JwtTokenUtil tokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        AuthenticationContext context = getDataFromHeader(request);
        setAuthentication(request, context);
        filterChain.doFilter(request, response);
    }

    private AuthenticationContext getDataFromHeader(HttpServletRequest request) {
        final String requestTokenHeader = request.getHeader("Authorization");
        final String requestUri = request.getRequestURI();
        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith(BEARER_PREFIX)) {
            jwtToken = requestTokenHeader.substring(7);

            try {
                username = tokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException exception) {
                error(ERR_MSG_INVALID_TOKEN, requestUri);
            } catch (ExpiredJwtException exception) {
                error(ERR_MSG_TOKEN_EXPIRED, requestUri);
            }
        } else {
            warn(WRN_MSG_INVALID_PREFIX, requestUri);
        }

        return new AuthenticationContext(jwtToken, username);
    }

    private void warn(String message, String requestUri) {
        log(message, log::warn, requestUri);
    }

    private void error(String message, String requestUri) {
        log(message, log::error, requestUri);
    }

    private void log(String message, Consumer<String> consumer, String requestUri) {
        if (!POST_URIS.contains(requestUri)) {
            consumer.accept(message);
        }
    }

    private void setAuthentication(HttpServletRequest request, AuthenticationContext context) {
        String username = context.getUsername();

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (!tokenUtil.isTokenExpired(context.getJwtToken())) {
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
