package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.PersonalCenterUserDTO;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.UserIncomeInfoDTO;
import com.cqut.czb.bn.entity.entity.*;
import com.cqut.czb.bn.service.AppPersonalCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppPersonalCenterImpl implements AppPersonalCenterService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserMapperExtra userMapperExtra;

    @Autowired
    UserIncomeInfoMapper userIncomeInfoMapper;

    @Autowired
    UserIncomeInfoMapperExtra userIncomeInfoMapperExtra;

    @Autowired
    PetrolSalesRecordsMapperExtra petrolSalesRecordsMapperExtra;

    @Autowired
    AppRouterMapperExtra appRouterMapperExtra;

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

    @Override
    public List<AppRouter> getAppRouters(AppRouter appRouter) {
        return appRouterMapperExtra.selectAppRouters(appRouter);
    }

    @Override
    public PersonalCenterUserDTO getUserEnterpriseInfo(String userId) {
        return userMapperExtra.GetUserEnterpriseInfo(userId);
    }
}
