package com.springsecuritypractice.oauth2.service;

import com.springsecuritypractice.oauth2.converter.OAuth2ConverterWrapper;
import com.springsecuritypractice.oauth2.model.Oauth2PracticeUser;
import com.springsecuritypractice.oauth2.repository.Oauth2PracticeUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuth2PracticeService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final Oauth2PracticeUserRepository repository;

    private final DefaultOAuth2UserService defaultUserService;

    private final OAuth2ConverterWrapper converter;

    @Override
    public DefaultOAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = defaultUserService.loadUser(request);
        Oauth2PracticeUser transientUser = converter.toEntity(oAuth2User);
        Oauth2PracticeUser persisted = repository.save(transientUser);

        return (DefaultOAuth2User) oAuth2User;
    }
}
