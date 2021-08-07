package com.weimengchao.auth.service;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface AuthService {

    /**
     * 退出登录
     *
     * @param accessToken
     * @return
     */
    @PostMapping("/auth/logout")
    Boolean logout(@RequestParam String accessToken);

}
