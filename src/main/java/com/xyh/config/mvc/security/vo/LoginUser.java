package com.xyh.config.mvc.security.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.xyh.pojo.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Security的核心对象之一，UserDetails
 */

@Data
public class LoginUser implements UserDetails {

    private User user;

    //权限字段
    private List<String> permissions;

    // 防止序列化
    @JSONField(serialize = false)
    private List<SimpleGrantedAuthority> authorities;

    public LoginUser() {}

    public LoginUser(User user, List<String> permissions) {
        this.user = user;
        this.permissions = permissions;
    }

    /**
     * 在其中得到其权限字段
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(authorities != null){
            return authorities;
        }
        //将permissions中的权限信息封装成SimpleGrantedAuthority
        authorities = permissions.stream().map(item->{
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(item);
            return authority;
        }).collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getAccount();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
