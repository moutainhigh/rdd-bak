package com.cqut.czb.auth.serviceImpl;

import com.cqut.czb.auth.jwt.JwtUser;
import com.cqut.czb.bn.dao.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 该类用于Security调用，获取用户信息
 */
@Service
public class AuthUserServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new JwtUser(userMapper.findUserByAccount(username));
    }
}
