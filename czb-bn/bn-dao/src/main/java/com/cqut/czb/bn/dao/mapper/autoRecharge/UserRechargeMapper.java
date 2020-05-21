package com.cqut.czb.bn.dao.mapper.autoRecharge;

import com.cqut.czb.bn.entity.dto.OfflineRecharge.IncomeIog;
import com.cqut.czb.bn.entity.dto.OfflineRecharge.UserRecharge;
import com.cqut.czb.bn.entity.dto.autoRecharge.UserRechargeDTO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface UserRechargeMapper {
    double getBalance(String userId);

    String getInfoId(String userId);

    /**
     * 插入充值人收益信息
     * @param incomeInfo
     * @return
     */
    boolean insert(IncomeIog incomeInfo);

    /**
     * 充值人员余额变化
     * @param userId
     * @param turnoverAmount
     * @return
     */
    boolean updateRecharge(@Param("userId") String userId, @Param("turnoverAmount") BigDecimal turnoverAmount);

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

    boolean insertBatchRecharge(@Param("userRecharge") List<UserRecharge> userRecharge);
}
