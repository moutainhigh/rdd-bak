package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.UserIncomeInfoMapper;
import com.cqut.czb.bn.dao.mapper.UserMapper;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.UserIncomeInfoDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.UserIncomeInfo;
import com.cqut.czb.bn.service.AppPersonalCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppPersonalCenterImpl implements AppPersonalCenterService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserIncomeInfoMapper userIncomeInfoMapper;

    @Override
    public User selectUser(String userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public List<UserIncomeInfoDTO> selectUserIncomeInfo(String userId) {
        return userIncomeInfoMapper.selectUserIncomeInfo(userId);
    }
}
