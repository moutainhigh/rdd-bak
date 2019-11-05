package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.appPersonalCenter.AppRouterDTO;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.MyIncomeLogDTO;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.PersonalCenterUserDTO;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.UserIncomeInfoDTO;
import com.cqut.czb.bn.entity.dto.petrolRecharge.PetrolRechargeInputDTO;
import com.cqut.czb.bn.entity.dto.petrolSaleInfo.AppPetrolSaleInfoOutputDTO;
import com.cqut.czb.bn.entity.entity.Petrol;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;

import java.util.List;

/**
 * 创建人：陈德强
 * 作用：app个人中心业务处理
 * 创建时间：2019/4/21
 */
public interface AppPersonalCenterService {

    /**
     * app查找用户收益信息
     *
     * @param userId
     * @return
     */
    UserIncomeInfoDTO selectUserIncomeInfo(String userId);

    /**
     * 获取用户购买的国通卡
     *
     * @param userId
     * @return
     */
    List<Petrol> getGTSoldPetrolForUser(String userId);

    List<AppPetrolSaleInfoOutputDTO> getPhysicalCardRechargeRecords(String userId, String petrolKind);

    /**
     * 修改油卡卡号
     *
     * @return
     */
    JSONResult modifyPetrolNum(String userId, PetrolRechargeInputDTO record);

    /**
     * 查出个人中心菜单展示
     *
     * @param appRouterDTO
     * @return
     */
    List<AppRouterDTO> getAppRouters(AppRouterDTO appRouterDTO);

    /**
     * 查出个人中心个人信息（包含企业）
     *
     * @param user
     * @return
     */
    PersonalCenterUserDTO getUserEnterpriseInfo(User user, String area);

    /**
     * 修改企业联系人
     *
     * @param personalCenterUserDTO
     * @return
     */
    boolean ModifyContactInfo(PersonalCenterUserDTO personalCenterUserDTO);

    /**
     * 查出个人收益记录
     *
     * @param myIncomeLogDTO
     * @return
     */
    List<MyIncomeLogDTO> selectIncomeLog(MyIncomeLogDTO myIncomeLogDTO, User user);
}
