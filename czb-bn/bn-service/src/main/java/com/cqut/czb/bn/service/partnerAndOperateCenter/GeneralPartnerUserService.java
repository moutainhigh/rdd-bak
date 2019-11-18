package com.cqut.czb.bn.service.partnerAndOperateCenter;

import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.GeneralPartnerUserPageDTO;
import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.UserIncomeStatisticDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;

/**
 * @ClassName: GeneralPartnerUserService
 * @Author: Iriya720
 * @Date: 2019/11/16
 * @Description: 普通合伙人管理借口
 * @version: v1.0
 */
public interface GeneralPartnerUserService {

    /**
     * 获取普通合伙人推广用户信息
     * @param user
     * @param pageDTO
     * @return
     */
    JSONResult getGeneralPartnerUserTableData(User user, GeneralPartnerUserPageDTO pageDTO);

    /**
     * 获取普通合伙人旗下的普通用户的收益
     * @param userIncomeStatisticDTO
     * @return
     */
    JSONResult getIncomeStatistic(UserIncomeStatisticDTO userIncomeStatisticDTO);

    /**
     * 获取普通合伙人旗下普通用户的推荐人数
     * @param userId
     * @return
     */
    JSONResult getPromoterStatistic(String userId);
}
