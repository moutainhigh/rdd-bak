package com.cqut.czb.bn.dao.mapper.autoRecharge;

import com.cqut.czb.bn.entity.dto.OfflineRecharge.IncomeInfo;
import com.cqut.czb.bn.entity.dto.OfflineRecharge.UserRecharge;
import org.apache.ibatis.annotations.Param;

public interface UserRechargeMapper {
    double getBalance(String userId);

    /**
     * 插入油卡充值信息
     * @param petrol
     * @return
     */
    boolean insertRecharge(UserRecharge petrol);

    /**
     * 插入充值人收益信息
     * @param incomeInfo
     * @return
     */
    boolean insert(IncomeInfo incomeInfo);

    /**
     * 充值人员余额变化
     * @param userId
     * @param turnoverAmount
     * @return
     */
    boolean updateRecharge(@Param("userId") String userId, @Param("turnoverAmount") double turnoverAmount);


}
