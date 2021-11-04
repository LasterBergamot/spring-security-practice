package com.springsecuritypractice.oauth2.service;

import com.springsecuritypractice.oauth2.converter.OAuth2ConverterWrapper;
import com.springsecuritypractice.oauth2.repository.Oauth2PracticeUserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class OAuth2PracticeUserService extends PracticeUserService<DefaultOAuth2UserService, OAuth2UserRequest, OAuth2User> {

    public OAuth2PracticeUserService(Oauth2PracticeUserRepository repository, OAuth2ConverterWrapper converter, DefaultOAuth2UserService userService) {
        super(repository, converter, userService);
    }
}
