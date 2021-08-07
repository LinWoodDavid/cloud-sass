package com.weimengchao.common.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
@ConditionalOnExpression("!'${security.oauth2.ignore-urls}'.isEmpty()")
@ConfigurationProperties(prefix = "security.oauth2")
public class PermitAllProperties {

    //放行的资源url
    @Getter
    @Setter
    private Set<String> ignoreUrls = new HashSet<>();

}
