package com.springsecuritypractice.oauth2.service;

import com.springsecuritypractice.oauth2.model.Oauth2PracticeUser;
import com.springsecuritypractice.oauth2.model.ResourceServer;
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
public class OAuth2PracticeService implements OAuth2UserService<OAuth2UserRequest, DefaultOAuth2User> {

    private final Oauth2PracticeUserRepository repository;

    private final DefaultOAuth2UserService userService;

    @Override
    public DefaultOAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = userService.loadUser(userRequest);
        Oauth2PracticeUser transientUser = convertToEntity(oAuth2User);
        Oauth2PracticeUser persisted = repository.save(transientUser);

        return (DefaultOAuth2User) oAuth2User;
    }

    private Oauth2PracticeUser convertToEntity(OAuth2User oAuth2User) {
        Oauth2PracticeUser user = null;

        if (oAuth2User instanceof DefaultOAuth2User) {
            DefaultOAuth2User defaultOAuth2User = ((DefaultOAuth2User) oAuth2User);

            user = Oauth2PracticeUser.builder()
                    .email(defaultOAuth2User.getAttribute("email"))
                    .name(defaultOAuth2User.getAttribute("name"))
                    .uniqueIdentifier(defaultOAuth2User.getAttribute("node_id"))
                    .resourceServer(ResourceServer.GITHUB)
                    .build();
        }

        return user;
    }
}
