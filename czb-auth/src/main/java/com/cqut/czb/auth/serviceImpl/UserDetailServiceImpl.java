package com.cqut.czb.auth.serviceImpl;

import com.cqut.czb.auth.service.UserDetailService;
import com.cqut.czb.bn.dao.mapper.UserMapper;
import com.cqut.czb.bn.dao.mapper.UserMapperExtra;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailService {
    @Autowired
    private UserMapperExtra userMapperExtra;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserMapperExtra userMapperExtra;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Boolean register(User user) {
        if(userMapperExtra.checkAccount(user.getUserAccount())) return new Boolean(false);
        user.setUserId(StringUtil.createId());
        user.setUserPsw(bCryptPasswordEncoder.encode(user.getUserPsw()));
        return userMapper.insertSelective(user) > 0;
    }

    @Override
    public Boolean checkAccount(User user) {
        return userMapperExtra.checkAccount(user.getUserAccount());
    }

    @Override
    public Boolean changePW(User user) {
        return userMapper.updateByPrimaryKeySelective(user)>0;
    }
}
