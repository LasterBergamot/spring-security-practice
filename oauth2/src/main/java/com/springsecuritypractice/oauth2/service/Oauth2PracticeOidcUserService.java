package com.springsecuritypractice.oauth2.service;

import com.springsecuritypractice.oauth2.converter.OAuth2ConverterWrapper;
import com.springsecuritypractice.oauth2.repository.Oauth2PracticeUserRepository;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
public class Oauth2PracticeOidcUserService extends PracticeUserService<OidcUserService, OidcUserRequest, OidcUser> {

    public Oauth2PracticeOidcUserService(Oauth2PracticeUserRepository repository, OAuth2ConverterWrapper converter, OidcUserService userService) {
        super(repository, converter, userService);
    }
}
