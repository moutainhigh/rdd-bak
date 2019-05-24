package com.cqut.czb.bn.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.AppRouterDTO;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.MyIncomeLogDTO;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.PersonalCenterUserDTO;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.UserIncomeInfoDTO;
import com.cqut.czb.bn.entity.dto.petrolSaleInfo.AppPetrolSaleInfoOutputDTO;
import com.cqut.czb.bn.entity.entity.*;
import com.cqut.czb.bn.service.AppPersonalCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AppPersonalCenterImpl implements AppPersonalCenterService {

    @Autowired
    UserMapperExtra userMapperExtra;

    @Autowired
    UserIncomeInfoMapperExtra userIncomeInfoMapperExtra;

    @Autowired
    PetrolSalesRecordsMapperExtra petrolSalesRecordsMapperExtra;

    @Autowired
    AppRouterMapperExtra appRouterMapperExtra;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private IncomeLogMapperExtra incomeLogMapperExtra;

    @Override
    public UserIncomeInfoDTO selectUserIncomeInfo(String userId) {
        UserIncomeInfoDTO userIncomeInfoDTO= userIncomeInfoMapperExtra.selectUserIncomeInfo(userId);
        if(userIncomeInfoDTO==null)
            return null;
        else {
            double totalIncome=0;//总收益
            double balance=0;//总余额
            if(userIncomeInfoDTO.getFanyongIncome()!=null){
                totalIncome= (BigDecimal.valueOf(userIncomeInfoDTO.getFanyongIncome()).add(BigDecimal.valueOf(totalIncome))).doubleValue();
            }else if(userIncomeInfoDTO.getShareIncome()!=null){
                totalIncome= (BigDecimal.valueOf(userIncomeInfoDTO.getShareIncome()).add(BigDecimal.valueOf(totalIncome))).doubleValue();
            }else if(userIncomeInfoDTO.getOtherIncome()!=null){
                totalIncome = (BigDecimal.valueOf(userIncomeInfoDTO.getOtherIncome()).add(BigDecimal.valueOf(totalIncome))).doubleValue();
            }else if(userIncomeInfoDTO.getWithdrawed()!=null){
                balance=(BigDecimal.valueOf(totalIncome).subtract(BigDecimal.valueOf(userIncomeInfoDTO.getWithdrawed()))).doubleValue();
            }
            userIncomeInfoDTO.setBlance(balance);
            userIncomeInfoDTO.setTotalIncome(totalIncome);
        }
        return userIncomeInfoDTO;
    }

    @Override
    public List<Petrol> getGTSoldPetrolForUser(String userId) {
        return petrolSalesRecordsMapperExtra.getGTSoldPetrolForUser(userId);
    }

    @Override
    public List<AppPetrolSaleInfoOutputDTO> getPhysicalCardRechargeRecords(String userId, String petrolKind) {
        return petrolSalesRecordsMapperExtra.getPhysicalCardsForUser(userId,petrolKind);
    }

    @Override
    public List<AppRouterDTO> getAppRouters(AppRouterDTO appRouterDTO) {
        return appRouterMapperExtra.selectAppRouters(appRouterDTO);
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
        System.out.println(personalCenterUserDTO.getUserPsw());
        boolean isLike=bCryptPasswordEncoder.matches(personalCenterUserDTO.getUserPsw(), checkUser.getUserPsw());
        if (!isLike) {
            System.out.println("密码错误");
            return false;
        } else {
            return userMapperExtra.ModifyContactInfo(personalCenterUserDTO)>0;
        }
    }

    @Override
    public List<MyIncomeLogDTO> selectIncomeLog(MyIncomeLogDTO myIncomeLogDTO) {
        return incomeLogMapperExtra.selectIncomeLog(myIncomeLogDTO);
    }
}
