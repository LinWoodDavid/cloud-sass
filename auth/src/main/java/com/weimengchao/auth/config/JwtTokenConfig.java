package com.weimengchao.auth.config;

import org.junit.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * 用 JWT 替换 redisToken
 * 上面 token 的存储用的是 redis 的方案，Spring Security OAuth2 还提供了 jdbc 和 jwt 的支持，jdbc 的暂不考虑，现在来介绍用 JWT 的方式来实现 token 的存储。
 * <p>
 * 用 JWT 的方式就不用把 token 再存储到服务端了，JWT 有自己特殊的加密方式，可以有效的防止数据被篡改，只要不把用户密码等关键信息放到 JWT 里就可以保证安全性。
 */
//@Configuration
public class JwtTokenConfig {

    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey("dev");
        return accessTokenConverter;
    }

    @Test
    public void a() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123456");
        System.out.println(encode);
    }
}
