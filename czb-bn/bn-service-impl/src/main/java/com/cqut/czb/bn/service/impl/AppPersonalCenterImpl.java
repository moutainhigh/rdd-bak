package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.PersonalCenterUserDTO;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.UserIncomeInfoDTO;
import com.cqut.czb.bn.entity.entity.*;
import com.cqut.czb.bn.service.AppPersonalCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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
    public PersonalCenterUserDTO getUserEnterpriseInfo(User user) {
        if (user==null){
            return null;
        }
        PersonalCenterUserDTO personalCenterUserDTO=userMapperExtra.GetUserEnterpriseInfo(user.getUserId());
        if(personalCenterUserDTO==null){
            personalCenterUserDTO = new PersonalCenterUserDTO();
            personalCenterUserDTO.setUserName(user.getUserName());
            personalCenterUserDTO.setUserAccount(user.getUserAccount());
            personalCenterUserDTO.setUserType(user.getUserType());
            personalCenterUserDTO.setUserRank(user.getUserRank());
        }

        return personalCenterUserDTO;
    }

    @Override
    public boolean ModifyContactInfo(PersonalCenterUserDTO personalCenterUserDTO) {
        if (personalCenterUserDTO==null){
            return false;
        }
        User checkUser = userMapperExtra.findUserByAccount(personalCenterUserDTO.getUserAccount());//通过电话号码来查询
        System.out.println(checkUser.getUserPsw());
        boolean isLike=bCryptPasswordEncoder.matches(checkUser.getUserPsw(),personalCenterUserDTO.getUserPsw());
        if (isLike) {
            System.out.println("密码错误");
            return false;
        } else {
            return userMapperExtra.ModifyContactInfo(personalCenterUserDTO)>0;
        }
    }
}
