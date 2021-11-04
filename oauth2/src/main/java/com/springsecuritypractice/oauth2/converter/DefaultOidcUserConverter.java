package com.springsecuritypractice.oauth2.converter;

import com.springsecuritypractice.oauth2.model.Oauth2PracticeUser;
import com.springsecuritypractice.oauth2.model.ResourceServer;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class DefaultOidcUserConverter implements OAuth2Converter {

    @Override
    public Oauth2PracticeUser toEntity(OAuth2User oAuth2User) {
        DefaultOidcUser defaultOidcUser = ((DefaultOidcUser) oAuth2User);

        return Oauth2PracticeUser.builder()
                .name(defaultOidcUser.getFullName())
                .email(defaultOidcUser.getEmail())
                .uniqueIdentifier(defaultOidcUser.getSubject())
                .resourceServer(ResourceServer.GOOGLE)
                .build();
    }

    @Override
    public Class<? extends OAuth2User> getChildClass() {
        return DefaultOidcUser.class;
    }
}
