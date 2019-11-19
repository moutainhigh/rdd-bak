package com.cqut.czb.bn.dao.mapper.partnerAndOperateCenter;

import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.GeneralPartnerUserPageDTO;
import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.UserIncomeStatisticDTO;
import com.cqut.czb.bn.entity.entity.partnerAndOperateCenter.GeneralPartnerUser;
import com.cqut.czb.bn.entity.entity.partnerAndOperateCenter.HumanAmountStatistic;

import java.util.List;

/**
 * @ClassName: GeneralPartnerUserMapperExtra
 * @Author: Iriya720
 * @Date: 2019/11/16
 * @Description:
 * @version: v1.0
 */
public interface GeneralPartnerUserMapperExtra {

    /**
     * 获取普通合伙人推广用户信息
     * @param pageDTO
     * @return
     */
    List<GeneralPartnerUser> getTableData(GeneralPartnerUserPageDTO pageDTO);


    /**
     * 获取普通用户的直推收益
     * @param userIncomeStatisticDTO
     * @return
     */
    HumanAmountStatistic getIncomeStatistic(UserIncomeStatisticDTO userIncomeStatisticDTO);


    /**
     * 获取普通合伙人旗下的普通用户推广人数
     * @param userId
     * @return
     */
    HumanAmountStatistic getPromoterStatistic(String userId);
}
