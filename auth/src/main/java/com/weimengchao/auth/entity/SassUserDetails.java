package com.weimengchao.auth.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class SassUserDetails extends User {

    /**
     * 账户编号 账户编号
     */
    private Long accountId;

    /**
     * 主账号编号
     */
    private Long parentId;

    /**
     * 用户名
     */
    private String userName;

    public SassUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, Long accountId, Long parentId, String userName) {
        super(username, password, authorities);
        this.accountId = accountId;
        this.parentId = parentId;
        this.userName = userName;
    }

    public SassUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, Long accountId, Long parentId, String userName) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.accountId = accountId;
        this.parentId = parentId;
        this.userName = userName;
    }

}
