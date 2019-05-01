package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.UserIncomeInfoDTO;
import com.cqut.czb.bn.entity.entity.Petrol;
import com.cqut.czb.bn.entity.entity.PetrolSalesRecords;
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

    @Autowired
    UserIncomeInfoMapperExtra userIncomeInfoMapperExtra;

    @Autowired
    PetrolSalesRecordsMapperExtra petrolSalesRecordsMapperExtra;

    @Override
    public User selectUser(String userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public List<UserIncomeInfoDTO> selectUserIncomeInfo(String userId) {
        return userIncomeInfoMapperExtra.selectUserIncomeInfo(userId);
    }

    @Override
    public List<Petrol> getGTSoldPetrolForUser(String userId) {
        return petrolSalesRecordsMapperExtra.getGTSoldPetrolForUser(userId);
    }

    @Override
    public List<PetrolSalesRecords> getPhysicalCardRechargeRecords(String userId, String petrolKind) {
        return petrolSalesRecordsMapperExtra.getPhysicalCardsForUser(userId,petrolKind);
    }
}
