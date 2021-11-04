package com.springsecuritypractice.oauth2.converter;

import com.springsecuritypractice.oauth2.model.Oauth2PracticeUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface OAuth2Converter {
    Oauth2PracticeUser toEntity(OAuth2User oAuth2User);
    Class<? extends OAuth2User> getChildClass();
}
