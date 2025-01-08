package org.example.model;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomAuthenticationToken extends AbstractAuthenticationToken {

    private final Integer userId;
    private final String credentials;  // 你可以传入任何认证信息，这里用 `null` 也可以

    // 构造函数
    public CustomAuthenticationToken(Integer userId, String credentials) {
        super(Collections.emptyList());  // 权限为空，后续可以设置权限
        this.userId = userId;
        this.credentials = credentials;
        setAuthenticated(false);  // 初始时尚未认证
    }

    // 返回用户ID
    public Integer getUserId() {
        return userId;
    }

    // 返回密码或凭证信息
    @Override
    public String getCredentials() {
        return credentials;
    }

    // 返回用户的权限
    @Override
    public List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("1".equals(userId)) { // 如果是管理员用户
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return authorities;
    }

    // 返回认证的主体
    @Override
    public Object getPrincipal() {
        return userId;
    }

    // 设置认证状态
    @Override
    public void setAuthenticated(boolean authenticated) {
        super.setAuthenticated(authenticated);
    }
}
