package com.xyh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xyh.base.enums.RoleEnum;
import com.xyh.config.mvc.security.vo.LoginUser;
import com.xyh.exceptions.CustomException;
import com.xyh.mapper.UserMapper;
import com.xyh.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 第二个核心对象之UserDetailService
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;



    /**
     *
     * @param account
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getAccount,account);
        User user = userMapper.selectOne(wrapper);

        if(Objects.isNull(user)){
            throw new CustomException("账号或密码错误");
        }

        if(user.getUserStatus().equals(1)){
            throw new CustomException("账号被封禁");
        }

        List<String> authorities = new ArrayList<>();
        authorities.add(RoleEnum.fromCode(user.getRole()).getRoleName());

        return new LoginUser(user,authorities);
    }
}
