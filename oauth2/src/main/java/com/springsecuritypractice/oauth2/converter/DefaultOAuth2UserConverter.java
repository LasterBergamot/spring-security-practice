package com.springsecuritypractice.oauth2.converter;

import com.springsecuritypractice.oauth2.model.Oauth2PracticeUser;
import com.springsecuritypractice.oauth2.model.ResourceServer;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class DefaultOAuth2UserConverter implements OAuth2Converter {

    @Override
    public Oauth2PracticeUser toEntity(OAuth2User oAuth2User) {
        DefaultOAuth2User defaultOAuth2User = ((DefaultOAuth2User) oAuth2User);

        return Oauth2PracticeUser.builder()
                .email(defaultOAuth2User.getAttribute("email"))
                .name(defaultOAuth2User.getAttribute("name"))
                .uniqueIdentifier(defaultOAuth2User.getAttribute("node_id"))
                .resourceServer(ResourceServer.GITHUB)
                .build();
    }

    @Override
    public Class<? extends OAuth2User> getChildClass() {
        return DefaultOAuth2User.class;
    }
}
