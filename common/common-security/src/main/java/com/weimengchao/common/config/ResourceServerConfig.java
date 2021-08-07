package com.weimengchao.common.config;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Value("${security.oauth2.client.client-id}")
    private String clientId;

    @Value("${security.oauth2.client.client-secret}")
    private String secret;

    @Value("${security.oauth2.authorization.check-token-access}")
    private String checkTokenEndpointUrl;

    @Autowired
    PermitAllProperties permitAllProperties;

    @Bean
    public RemoteTokenServices tokenService() {
        RemoteTokenServices tokenService = new RemoteTokenServices();
        tokenService.setClientId(clientId);
        tokenService.setClientSecret(secret);
        tokenService.setCheckTokenEndpointUrl(checkTokenEndpointUrl);
        return tokenService;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenServices(tokenService());
        AuthenticationEntryPoint authenticationEntryPoint = new OAuth2AuthenticationEntryPoint();
        ((OAuth2AuthenticationEntryPoint) authenticationEntryPoint).setExceptionTranslator(new Auth2ResponseExceptionTranslator());
        resources.authenticationEntryPoint(authenticationEntryPoint);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>
                .ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
        //所有请求必须认证通过
//        registry
//                //下边的路径放行
//                .antMatchers("/v2/api-docs", "/swagger-resources/configuration/ui",
//                        "/swagger-resources", "/swagger-resources/configuration/security",
//                        "/swagger-ui.html", "/course/coursebase/**"
//                        ,"/**","/webjars/**"
//                ).permitAll()
//        ;
        //让他们可以通过spring的加密体系
//        registry.antMatchers(
//                "/swagger-ui.html",
//                "/swagger-resources/**",
//                "/v2/api-docs",
//                "/webjars/**"
//        ).permitAll();

        //放行不用授权的资源
        if (CollectionUtils.isNotEmpty(permitAllProperties.getIgnoreUrls())) {
            permitAllProperties.getIgnoreUrls().forEach(antPattern ->
//                    registry.antMatchers(antPattern).permitAll().anyRequest().authenticated()
                            registry.antMatchers(antPattern).permitAll()
            );
        }
        registry.antMatchers("/**").authenticated();
        super.configure(http);
    }
}
