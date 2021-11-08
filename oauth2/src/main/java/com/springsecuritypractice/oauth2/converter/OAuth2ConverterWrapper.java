package com.springsecuritypractice.oauth2.converter;

import com.springsecuritypractice.oauth2.model.Oauth2PracticeUser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2ConverterWrapper {

    private final List<OAuth2Converter> converters;

    public Oauth2PracticeUser toEntity(OAuth2User oAuth2User) {
        return converters
                .stream()
                .filter(converter -> converter.getChildClass().equals(oAuth2User.getClass()))
                .map(converter -> converter.toEntity(oAuth2User))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No suitable OAuth2Converter found!"));
    }
}
