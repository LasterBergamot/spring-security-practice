package com.springsecuritypractice.oauth2.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OauthController {

    @GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        Map<String, Object> attributes = new HashMap<>();

        // Google
        if (principal instanceof DefaultOidcUser) {
            attributes = Collections.singletonMap("name", principal.getAttribute("name"));
            // GitHub
        } else if (principal instanceof DefaultOAuth2User) {
            attributes = Collections.singletonMap("name", principal.getAttribute("login"));
        }

        return attributes;
    }
}
