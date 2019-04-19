package com.cqut.czb.auth.jwt;

import com.cqut.czb.bn.entity.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * 该类用于Security调用的用户信息实体类
 */
public class JwtUser implements UserDetails {

    private String id;

    private String account;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtUser() {
    }

    // 写一个能直接使用user创建jwtUser的构造器
    public JwtUser(User user) {
        id = user.getUserId();
        account = user.getUserAccount();
        username = user.getUserName();
        password = user.getUserPsw();
        authorities = Collections.singleton(new SimpleGrantedAuthority(user.getUserId()));
    }

    // 获取权限信息，目前博主只会拿来存角色。。
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getAccount() {
        return account;
    }

    // 账号是否未过期，默认是false，有要求可改
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 账号是否未锁定，默认是false，有要求可改
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 账号凭证是否未过期，默认是false，有要求可改
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 这个有点抽象不会翻译，默认也是false，有要求可改
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "JwtUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}

