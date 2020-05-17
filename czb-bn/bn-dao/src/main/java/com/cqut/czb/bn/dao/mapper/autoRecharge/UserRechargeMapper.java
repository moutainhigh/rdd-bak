package com.cqut.czb.bn.dao.mapper.autoRecharge;

import com.cqut.czb.bn.entity.dto.OfflineRecharge.IncomeInfo;
import com.cqut.czb.bn.entity.dto.OfflineRecharge.UserRecharge;
import com.cqut.czb.bn.entity.dto.autoRecharge.UserRechargeDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 被充值人的余额更新
     * @param petrol
     * @return
     */
    boolean update(UserRecharge petrol);

    /**
     * 查询充值记录
     * @param pageDTO
     * @return
     */
    List<UserRecharge> getRechargeDetails(UserRechargeDTO pageDTO);

    double getTotalRechargeAmount(String userId);

    boolean insertBatchRecharge(@Param("petrolNums")String[] petrolNums, UserRechargeDTO userRechargeDTO);
}
