package com.weimengchao.auth.config;

import com.weimengchao.auth.entity.SassUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component(value = "kiteUserDetailsService")
public class KiteUserDetailsService implements UserDetailsService {

    /**
     * 把用户名、密码和所属角色都写在代码里了，
     * 正式环境中，这里应该是从数据库或者其他地方根据用户名将加密后的密码及所属角色查出来的。账号 admin ，密码 123456，稍后在换取 token 的时候会用到。并且给这个用户设置 "ROLE_ADMIN" 角色。
     *
     * @param username 用户名
     * @return UserDetails 用户详情
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername username={}", username);
        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("the username is empty; username:" + username);
        }

        //通过userName查询账户信息
//        AccountVo accountInfo = scoringFeignClient.getAccountByUserName(username);
//        if (accountInfo == null) {
//            throw new UsernameNotFoundException("the user is not found; username:" + username);
//        }
        String password = "123456";
        //TODO 通过角色id查询权限
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("get"));
        authorities.add(new SimpleGrantedAuthority("update"));
//        AuthorityUtils.commaSeparatedStringToAuthorityList("read,ROLE_USER");//设置权限和角色
        // 1. commaSeparatedStringToAuthorityList放入角色时需要加前缀ROLE_，而在controller使用时不需要加ROLE_前缀
        // 2. 放入的是权限时，不能加ROLE_前缀，hasAuthority与放入的权限名称对应即可
        //        return new User(username, password(), authorities);

        SassUserDetails myUserDetails = new SassUserDetails(username, password, authorities
                , 10000L, 1L, "David");
        return myUserDetails;

//        return getUserDetails(accountService.findOneByUserName(username), username, null);
    }
    // 用户角色也应在数据库中获取
//    String role = "ROLE_ADMIN";
//    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority(role));
//    // 线上环境应该通过用户名查询数据库获取加密后的密码
//    String password = passwordEncoder.encode("123456");
//        log.debug("password:{}", password);

}
