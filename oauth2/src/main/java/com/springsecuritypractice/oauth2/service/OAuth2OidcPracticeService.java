package com.springsecuritypractice.oauth2.service;

import com.springsecuritypractice.oauth2.model.Oauth2PracticeUser;
import com.springsecuritypractice.oauth2.model.ResourceServer;
import com.springsecuritypractice.oauth2.repository.Oauth2PracticeUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2OidcPracticeService implements OAuth2UserService<OidcUserRequest, OidcUser> {

    private final Oauth2PracticeUserRepository repository;

    private final OidcUserService userService;

    @Override
    public OidcUser loadUser(OidcUserRequest request) throws OAuth2AuthenticationException {
        OidcUser oAuth2User = userService.loadUser(request);
        Oauth2PracticeUser transientUser = convertToEntity(oAuth2User);
        Oauth2PracticeUser persisted = repository.save(transientUser);

        return oAuth2User;
    }

    private Oauth2PracticeUser convertToEntity(OidcUser oidcUser) {
        Oauth2PracticeUser user = null;

        if (oidcUser instanceof DefaultOidcUser) {
            DefaultOidcUser defaultOidcUser = ((DefaultOidcUser) oidcUser);

            user = Oauth2PracticeUser.builder()
                    .name(defaultOidcUser.getFullName())
                    .email(defaultOidcUser.getEmail())
                    .uniqueIdentifier(defaultOidcUser.getSubject())
                    .resourceServer(ResourceServer.GOOGLE)
                    .build();
        }

        return user;
    }
}
