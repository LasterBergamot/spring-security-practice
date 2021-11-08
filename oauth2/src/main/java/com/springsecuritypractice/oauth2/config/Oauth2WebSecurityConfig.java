package com.springsecuritypractice.oauth2.config;

import com.springsecuritypractice.oauth2.service.OAuth2PracticeUserService;
import com.springsecuritypractice.oauth2.service.Oauth2PracticeOidcUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class Oauth2WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private OAuth2PracticeUserService userService;

    @Autowired
    private Oauth2PracticeOidcUserService oidcUserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().antMatchers("/", "/error", "/webjars/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .logout()
                .logoutSuccessUrl("/").permitAll()
                .and()
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(userService)
                .oidcUserService(oidcUserService);
    }
}
