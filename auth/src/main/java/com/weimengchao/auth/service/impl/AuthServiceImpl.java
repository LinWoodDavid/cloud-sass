package com.weimengchao.auth.service.impl;

import com.weimengchao.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;

public class AuthServiceImpl implements AuthService {

    @Autowired
    ConsumerTokenServices consumerTokenServices;

    @Override
    public Boolean logout(String accessToken) {
        return consumerTokenServices.revokeToken(accessToken);
    }
}
