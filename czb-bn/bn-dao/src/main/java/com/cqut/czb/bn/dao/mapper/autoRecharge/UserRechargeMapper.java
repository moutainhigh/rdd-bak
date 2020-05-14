package com.cqut.czb.bn.dao.mapper.autoRecharge;

import com.cqut.czb.bn.entity.dto.OfflineRecharge.UserRecharge;
import org.apache.ibatis.annotations.Param;

public interface UserRechargeMapper {
    double getBalance(String userId);

    int insertRecharge(UserRecharge petrol);

    /**
     * 充值人员余额变化
     * @param userId
     * @param turnoverAmount
     * @return
     */
    boolean updateRecharge(@Param("userId") String userId, @Param("turnoverAmount") double turnoverAmount);

    /**
     * 被充值人的余额更新
     * @param petrol
     * @return
     */
    boolean update(UserRecharge petrol);
}
