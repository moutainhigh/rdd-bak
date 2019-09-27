package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.*;
import com.cqut.czb.bn.entity.dto.petrolSaleInfo.AppPetrolSaleInfoOutputDTO;
import com.cqut.czb.bn.entity.entity.Petrol;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.UserRole;
import com.cqut.czb.bn.entity.entity.VipAreaConfig;
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
    UserMapper userMapper;

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

    @Autowired
    UserRoleMapperExtra userRoleMapperExtra;

    @Autowired
    private VipAreaConfigMapperExtra vipAreaConfigMapperExtra;

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
            }
            if(userIncomeInfoDTO.getShareIncome()!=null){
                totalIncome= (BigDecimal.valueOf(userIncomeInfoDTO.getShareIncome()).add(BigDecimal.valueOf(totalIncome))).doubleValue();
            }
            if(userIncomeInfoDTO.getOtherIncome()!=null){
                totalIncome = (BigDecimal.valueOf(userIncomeInfoDTO.getOtherIncome()).add(BigDecimal.valueOf(totalIncome))).doubleValue();
            }
            if(userIncomeInfoDTO.getWithdrawed()!=null){
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
    public PersonalCenterUserDTO getUserEnterpriseInfo(User user, String area) {
        if (user==null){
            return null;
        }
        User user1 = userMapper.selectByPrimaryKey(user.getUserId());
        UserRoleDTO userRole = new UserRoleDTO();
        userRole.setUserId(user.getUserId());
        List<UserRoleDTO> userRoles = userRoleMapperExtra.selectUserRoleName(userRole); //查询用户角色信息
        PersonalCenterUserDTO personalCenterUserDTO=userMapperExtra.GetUserEnterpriseInfo(user.getUserId());
        VipAreaConfig vipAreaConfig = vipAreaConfigMapperExtra.selectVipAreaConfigByArea(area);

        if(personalCenterUserDTO==null){
            personalCenterUserDTO = new PersonalCenterUserDTO();
            personalCenterUserDTO.setUserName(user1.getUserName());
            personalCenterUserDTO.setUserAccount(user1.getUserAccount());
            personalCenterUserDTO.setUserType(user1.getUserType());
            personalCenterUserDTO.setUserRank(user1.getUserRank());
            personalCenterUserDTO.setIsVip(user1.getIsVip());
            if (userRoles!=null && userRoles.size()!=0 && userRoles.get(0).getRoleName()!=null){  //加入用户角色信息
                personalCenterUserDTO.setRoleName(userRoles.get(0).getRoleName());
            }
            if(vipAreaConfig == null){
                personalCenterUserDTO.setHaveVip(0);
            }else{
                personalCenterUserDTO.setHaveVip(1);
            }
        }else {
            if (userRoles!=null && userRoles.size()!=0 && userRoles.get(0).getRoleName()!=null){  //加入用户角色信息
                personalCenterUserDTO.setRoleName(userRoles.get(0).getRoleName());
            }
        }

        if(vipAreaConfig == null){
            personalCenterUserDTO.setHaveVip(0);
        }else{
            personalCenterUserDTO.setHaveVip(1);
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
    public List<MyIncomeLogDTO> selectIncomeLog(MyIncomeLogDTO myIncomeLogDTO,User user) {

        List<MyIncomeLogDTO> myIncomeLogDTOS=incomeLogMapperExtra.selectIncomeLog(myIncomeLogDTO);
        if(myIncomeLogDTOS==null){
            return null;
        }
        for(int i=0;i<myIncomeLogDTOS.size();i++){

            //添加几级收益
            if(myIncomeLogDTOS.get(i).getCommissionLevel()==null){
                myIncomeLogDTOS.get(i).setIncomeClass("未知收益");
            }else{
                if(myIncomeLogDTOS.get(i).getCommissionLevel()==1){
                    myIncomeLogDTOS.get(i).setIncomeClass("一级收益");
                    System.out.println( myIncomeLogDTOS.get(i).getIncomeClass());
                }else if(myIncomeLogDTOS.get(i).getCommissionLevel()==2){
                    myIncomeLogDTOS.get(i).setIncomeClass("二级收益");
                    System.out.println( myIncomeLogDTOS.get(i).getIncomeClass());
                }
            }

            //添加来源姓名
            if(myIncomeLogDTOS.get(i).getCommissionSourceUser()==null){
                myIncomeLogDTOS.get(i).setUserName("无实名");
                myIncomeLogDTOS.get(i).setUserAccount("***********");
            }
            else
            {
                User user1=userMapper.selectByPrimaryKey(myIncomeLogDTOS.get(i).getCommissionSourceUser());
                if(user1==null){
                    myIncomeLogDTOS.get(i).setUserName("无实名");
                    myIncomeLogDTOS.get(i).setUserAccount("***********");
                }else {
                    myIncomeLogDTOS.get(i).setUserAccount(user1.getUserAccount());
                    myIncomeLogDTOS.get(i).setUserName(user1.getUserName());
                }
            }

            if(myIncomeLogDTO.getType()==4)//系统补贴
            {
                myIncomeLogDTOS.get(i).setUserAccount("系统补贴");
            }
        }
        return myIncomeLogDTOS;
    }
}
