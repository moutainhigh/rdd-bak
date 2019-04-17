package com.cqut.czb.auth.service;

import com.cqut.czb.auth.serviceI.UserDetailService;
import com.cqut.czb.bn.dao.mapper.UserMapper;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailService{
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Boolean register(User user) {
        if(userMapper.checkAccount(user.getAccount())) return new Boolean(false);
        user.setUserId(StringUtil.createId());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userMapper.insertUser(user);
    }

    @Override
    public Boolean checkAccount(User user) {
        return userMapper.checkAccount(user.getAccount());
    }
}
