package com.cqut.czb.bn.service.impl.autoRecharge;

import com.cqut.czb.bn.dao.mapper.autoRecharge.UserRechargeMapper;
import com.cqut.czb.bn.entity.dto.OfflineRecharge.IncomeInfo;
import com.cqut.czb.bn.entity.dto.OfflineRecharge.UserRecharge;
import com.cqut.czb.bn.entity.entity.Petrol;
import com.cqut.czb.bn.service.autoRecharge.UserRechargeService;
import com.cqut.czb.bn.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserRechargeServiceimpl implements UserRechargeService {

    @Autowired
    UserRechargeMapper userRechargeMapper;

    /**
     * 插入充值记录
     * @param userId
     * @param userRecharge
     * @return
     */
    @Override
    public int insertRecharge(String userId, UserRecharge userRecharge) {
        UserRecharge petrol = new UserRecharge();
        //订单标识
        String orgId = System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15);
        petrol.setRecordId(orgId);
        petrol.setPetrolNum(userRecharge.getPetrolNum());
        petrol.setBuyerId(userId);
        petrol.setPaymentMethod(2);
        petrol.setThirdOrderId(getOrderIdByUUId(userId));
        petrol.setTurnoverAmount(userRecharge.getTurnoverAmount());
        petrol.setTransactionTime(userRecharge.getTransactionTime());
        petrol.setState(1);
        petrol.setRecordType(3);
        petrol.setIsRecharged(0);
        petrol.setPetrolKind(1);
        petrol.setDenomination(userRecharge.getTurnoverAmount());
        petrol.setCurrentPrice(userRecharge.getTurnoverAmount());
        boolean petr = userRechargeMapper.insertRecharge(petrol);
        boolean isBalance = userRechargeMapper.updateRecharge(userId,userRecharge.getTurnoverAmount());

        IncomeInfo incomeInfo = new IncomeInfo();
        incomeInfo.setInfoId(StringUtil.createId());
        incomeInfo.setUserId(userId);
        incomeInfo.setOtherIncome(userRechargeMapper.getBalance(userId));
        boolean info = userRechargeMapper.insert(incomeInfo);
        if(petr && isBalance && info)
            return 1;
        else
            return -1;
    }

    /**
     * 获取余额
     * @param userId
     * @return
     */
    @Override
    public double getBalance(String userId) {
        return userRechargeMapper.getBalance(userId);
    }

    /**
     * 生成支付订单
     * @param userId
     * @return
     */
    public String getOrderIdByUUId(String userId) {
        int machineId = 1;//最大支持1-9个集群机器部署
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if(hashCodeV < 0) {//有可能是负数
            hashCodeV = - hashCodeV;
        }
//         0 代表前面补充0     
//         4 代表长度为4     
//         d 代表参数为正数型
         return machineId + String.format("%015d", hashCodeV) + userId;
    }
}
