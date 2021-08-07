package com.weimengchao.auth.converter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class MyUserAuthenticationConverter extends DefaultUserAuthenticationConverter {

    final String USERDETAIL = "user_detail";

    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
//        Map<String, ?> map = super.convertUserAuthentication(authentication);
//        Map<String, Object> response = new LinkedHashMap();
//        response.putAll(map);
//        response.put(USERNAME, authentication);
//        return response;

        Map<String, Object> response = new LinkedHashMap<String, Object>();
        response.put(USERNAME, authentication.getPrincipal());
        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        }
        return response;
    }
}
