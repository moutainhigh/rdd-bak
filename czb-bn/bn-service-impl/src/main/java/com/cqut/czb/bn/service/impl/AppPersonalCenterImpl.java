package com.cqut.czb.bn.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.entity.dto.CouponsSaleRecordsDTO;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.*;
import com.cqut.czb.bn.entity.dto.petrolRecharge.PetrolRechargeInputDTO;
import com.cqut.czb.bn.entity.dto.petrolSaleInfo.AppPetrolSaleInfoOutputDTO;
import com.cqut.czb.bn.entity.entity.*;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.AppPersonalCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    DictMapperExtra dictMapperExtra;

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

        try {
            Double teamIncome = this.getTeamIncome(userId);
            userIncomeInfoDTO.setMoreIncome(teamIncome);
            Double teamIncome2 = this.getTeamIncome2(userId);
            userIncomeInfoDTO.setMoreIncome2(teamIncome2);
        } catch (Exception e){
            e.printStackTrace();
        }

        return userIncomeInfoDTO;
    }

    @Override
    public String getMoreIncomeHelp() {
        Dict info = dictMapperExtra.selectDictByName("moreIncomeHelp");
        return info.getContent();
    }

    private Double getTeamIncome(String userId) {
        int num = userMapperExtra.selectTeamTotal(userId);
        Dict teamIncomeOnePerson = dictMapperExtra.selectDictByName("teamIncomeOnePerson");
        if (teamIncomeOnePerson!=null){
            return num * Double.parseDouble(teamIncomeOnePerson.getContent());
        } else {
            return 0.0;
        }
    }

    private Double getTeamIncome2(String userId) {
        int num = userMapperExtra.selectTeamTotal(userId);
        Dict teamIncomeOnePerson = dictMapperExtra.selectDictByName("teamIncomeOnePerson2");
        if (teamIncomeOnePerson!=null){
            return num * Double.parseDouble(teamIncomeOnePerson.getContent());
        } else {
            return 0.0;
        }
    }

    @Override
    public List<Petrol> getGTSoldPetrolForUser(String userId) {
        List<Petrol> list=petrolSalesRecordsMapperExtra.getGTSoldPetrolForUser(userId);
        return list;
    }

    @Override
    public List<AppPetrolSaleInfoOutputDTO> getPhysicalCardRechargeRecords(String userId, String petrolKind) {
        List<AppPetrolSaleInfoOutputDTO> list = petrolSalesRecordsMapperExtra.getPhysicalCardsForUser(userId,petrolKind);
        //解决app油卡购买时间显示问题
        for(AppPetrolSaleInfoOutputDTO appPetrolSaleInfoOutputDTO : list){
            appPetrolSaleInfoOutputDTO.setUpdateAt(appPetrolSaleInfoOutputDTO.getCreateAt());
            appPetrolSaleInfoOutputDTO.setTransactionTime(appPetrolSaleInfoOutputDTO.getCreateAt());
        }
        return list;
    }

    @Override
    public List<CouponsSaleRecordsDTO> getCouponsSaleRecords(String userId) {
        List<CouponsSaleRecordsDTO> list = petrolSalesRecordsMapperExtra.getCouponsSaleRecords(userId);
        //解决app油卡购买时间显示问题
        for(CouponsSaleRecordsDTO couponsSaleRecordsDTO : list){
            JSONArray orderInfo = JSON.parseArray(couponsSaleRecordsDTO.getOrderInfo());
            JSONObject object = (JSONObject)orderInfo.get(0);
            couponsSaleRecordsDTO.setOrderInfo((String) object.get("CardPass"));
            couponsSaleRecordsDTO.setUpdateAt(couponsSaleRecordsDTO.getCreateAt());
            couponsSaleRecordsDTO.setTransactionTime(couponsSaleRecordsDTO.getCreateAt());
        }
        return list;
    }

    @Override
    public JSONResult modifyPetrolNum(String userId, PetrolRechargeInputDTO record) {
        JSONResult<Boolean> jsonResult = new JSONResult<>();

        if (record.getUpdatePetrolNum() == null || "".equals(record.getUpdatePetrolNum())) {
            jsonResult.setData(false);
            jsonResult.setMessage("要修改的卡号不能为空");
            return jsonResult;
        }
        // 判断要更新的油卡卡号是否跟数据库中的卡号重复
        List<Petrol> repeatPetrol = petrolSalesRecordsMapperExtra.judgePetrolNumRepeat(record.getUpdatePetrolNum());
        if (repeatPetrol != null && repeatPetrol.size() > 0) {
            jsonResult.setData(false);
            jsonResult.setMessage("该卡号已被使用");
            return jsonResult;
        }
        // 根据油卡寄送表与地址表查找出该用户收货人
        record.setUserId(userId);
        List<String> receiverList = petrolSalesRecordsMapperExtra.getReceiver(record);
        if (receiverList == null || receiverList.size() == 0) {
            jsonResult.setData(false);
            jsonResult.setMessage("该油卡的持有人不存在");
            return jsonResult;
        }
        if (record.getUpdatePetrolNum().length() != 16){
            jsonResult.setData(false);
            jsonResult.setMessage("该油卡格式错误");
            return jsonResult;
        }
        // 用户修改卡号，加前缀RDD
        record.setUpdatePetrolNum("RDD" + record.getUpdatePetrolNum() + receiverList.get(0));
        // 修改油卡卡号
        jsonResult.setData(petrolSalesRecordsMapperExtra.appUpdatePetrolNum(record) > 0);
        return jsonResult;
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
            personalCenterUserDTO.setUserId(user.getUserId());
            personalCenterUserDTO.setUserName(user1.getUserName());
            personalCenterUserDTO.setUserAccount(user1.getUserAccount());
            personalCenterUserDTO.setUserType(user1.getUserType());
            personalCenterUserDTO.setUserRank(user1.getUserRank());
            personalCenterUserDTO.setIsVip(user1.getIsVip());
            personalCenterUserDTO.setBindingid(user1.getBindingid());
            if (personalCenterUserDTO.getBindingid()!=null){
                personalCenterUserDTO.setBindingAccount(userMapperExtra.selectBindingAccount(personalCenterUserDTO.getBindingid()));
            }else {
                personalCenterUserDTO.setBindingAccount(null);
            }
            if (userRoles!=null && userRoles.size()!=0 && userRoles.get(0).getRoleName()!=null){  //加入用户角色信息
                personalCenterUserDTO.setRoleName(userRoles.get(0).getRoleName());
                personalCenterUserDTO.setRoleNameList(userRoles);
            }
            if(vipAreaConfig == null){
                personalCenterUserDTO.setHaveVip(0);
            }else{
                personalCenterUserDTO.setHaveVip(1);
            }
        }else {
            if (userRoles!=null && userRoles.size()!=0 && userRoles.get(0).getRoleName()!=null){  //加入用户角色信息
                personalCenterUserDTO.setRoleName(userRoles.get(0).getRoleName());
                personalCenterUserDTO.setRoleNameList(userRoles);
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
        List<MyIncomeLogDTO> myIncomeLogDTOS=new ArrayList<>();
        if(myIncomeLogDTO.getType()==0)
            myIncomeLogDTOS=incomeLogMapperExtra.selectIncomeLog(myIncomeLogDTO);
        else
            myIncomeLogDTOS=incomeLogMapperExtra.selectIncomeLog2(myIncomeLogDTO);
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
                myIncomeLogDTOS.get(i).setUserAccount("***********");
            }

            if(myIncomeLogDTO.getType()==4)//系统补贴
            {
                myIncomeLogDTOS.get(i).setUserAccount("系统补贴");
            }
        }
        return myIncomeLogDTOS;
    }
}
