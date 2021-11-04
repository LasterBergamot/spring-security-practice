package com.springsecuritypractice.oauth2.service;

import com.springsecuritypractice.oauth2.converter.OAuth2ConverterWrapper;
import com.springsecuritypractice.oauth2.model.Oauth2PracticeUser;
import com.springsecuritypractice.oauth2.repository.Oauth2PracticeUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public abstract class PracticeUserService<S extends OAuth2UserService<R, U>, R extends OAuth2UserRequest, U extends OAuth2User> implements OAuth2UserService<R, U> {

    private final Oauth2PracticeUserRepository repository;

    private final OAuth2ConverterWrapper converter;

    private final S userService;

    public U loadUser(R request) {
        U oauth2User = userService.loadUser(request);
        Oauth2PracticeUser transientUser = converter.toEntity(oauth2User);
        Oauth2PracticeUser persisted = repository.save(transientUser);

        return oauth2User;
    }
}
