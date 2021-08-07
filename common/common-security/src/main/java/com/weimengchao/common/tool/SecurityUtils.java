package com.weimengchao.common.tool;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

public class SecurityUtils {

    public static Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authentication;
    }

    public static List<String> getAuthorities(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> result = new ArrayList<>();
        if (CollectionUtils.isEmpty(authorities)) {
            return result;
        }
        for (GrantedAuthority authority : authorities) {
            result.add(authority.getAuthority());
        }
        return result;
    }

    public static List<String> getAuthorities() {
        Authentication authentication = getAuthentication();
        return getAuthorities(authentication);
    }

    public static LinkedHashMap<String, Object> getUser(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal == null) {
            return new LinkedHashMap<>();
        }
        if (principal instanceof LinkedHashMap) {
            LinkedHashMap<String, Object> userDetails = (LinkedHashMap<String, Object>) principal;
            return userDetails;
        }
        return null;
    }

    public static Long getAccountId() {
        Authentication authentication = getAuthentication();
        LinkedHashMap<String, Object> user = getUser(authentication);
        return getAccountId(user);
    }

    public static Long getParentId() {
        Authentication authentication = getAuthentication();
        LinkedHashMap<String, Object> user = getUser(authentication);
        return getParentId(user);
    }

    public static String getUserName() {
        Authentication authentication = getAuthentication();
        LinkedHashMap<String, Object> user = getUser(authentication);
        return getUserName(user);
    }

    public static Long getAccountId(LinkedHashMap userDetails) {
        Object accountId = userDetails.get("accountId");
        if (accountId == null) {
            return null;
        }
        return Long.valueOf(String.valueOf(accountId));
    }

    public static Long getParentId(LinkedHashMap userDetails) {
        Object parentId = userDetails.get("parentId");
        if (parentId == null) {
            return null;
        }
        return Long.valueOf(String.valueOf(parentId));
    }

    public static String getUserName(LinkedHashMap userDetails) {
        Object userName = userDetails.get("userName");
        if (userName == null) {
            return null;
        }
        return String.valueOf(userName);
    }


}
