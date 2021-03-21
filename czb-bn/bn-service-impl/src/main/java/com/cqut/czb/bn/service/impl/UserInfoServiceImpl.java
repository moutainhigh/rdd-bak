package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.UserInfoMapperExtra;
import com.cqut.czb.bn.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    UserInfoMapperExtra userInfoMapperExtra;

    public String getUserAccount(String userId) {
        return userInfoMapperExtra.getUserAccount(userId);
    }
}
