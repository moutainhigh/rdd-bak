package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.appPersonalCenter.UserIncomeInfoDTO;
import com.cqut.czb.bn.entity.entity.Petrol;
import com.cqut.czb.bn.entity.entity.PetrolSalesRecords;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.UserIncomeInfo;

import java.util.List;

/**
 * 创建人：陈德强
 * 作用：app个人中心业务处理
 * 创建时间：2019/4/21
 */
public interface AppPersonalCenterService {

    /**
     *app查找用户相关信息
     * @param userId
     * @return
     */
    User selectUser(String userId);

    /**
     * app查找用户收益信息
     * @param userId
     * @return
     */
    List<UserIncomeInfoDTO> selectUserIncomeInfo(String userId);

    /**
     * 获取用户购买的国通卡
     * @param userId
     * @return
     */
    List<Petrol> getGTSoldPetrolForUser(String userId);

    List<PetrolSalesRecords> getPhysicalCardRechargeRecords(String userId,String petrolKind);


}
