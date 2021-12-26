package com.cqut.czb.bn.service.impl.electricityRecharge;

import com.cqut.czb.bn.dao.mapper.electricityRecharge.ElectricityConsumptionMapperExtra;
import com.cqut.czb.bn.entity.dto.autoRecharge.UserRechargeDTO;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.dto.directCustomers.CustomerManageDto;
import com.cqut.czb.bn.entity.dto.directCustomers.ElectricityDto;
import com.cqut.czb.bn.entity.dto.directCustomers.ElectricityRechargeDto;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.electricityRecharge.ElectricityConsumption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class ElectricityConsumptionImpl implements ElectricityConsumption {

    @Autowired
    ElectricityConsumptionMapperExtra electricityConsumptionMapperExtra;

    @Override
    public double getBalance(String userId) {
        double balance = electricityConsumptionMapperExtra.getBalance(userId);
        System.out.println("查询余额" + balance);
        return balance;
    }

    /**
     * 获取总充值金额
     * @param user
     * @return
     */
    @Override
    public JSONResult getTotalRechargeAmount(User user) {
        double total = electricityConsumptionMapperExtra.getTotalRechargeAmount(user.getUserId());
        return new JSONResult("总充值金额查询成功",200,total);
    }

    @Override
    public JSONResult insertElectricityRecharge(User user, UserRechargeDTO userRechargeDTO) {
        String[] petrolNums;
        String[] regionals;

        if (userRechargeDTO.getPetrolNums() != null && userRechargeDTO.getPetrolNums() != " "){
            petrolNums = userRechargeDTO.getPetrolNums().split(",");
        }else {
            return new JSONResult("充值失败",200);
        }

        if (userRechargeDTO.getRegional() != null && userRechargeDTO.getRegional() != " "){
            regionals = userRechargeDTO.getRegional().split(",");
        }else {
            return new JSONResult("充值失败，地址获取失败",200);
        }

        //本次总充值金额
        double total = userRechargeDTO.getTurnoverAmount() * petrolNums.length;
        BigDecimal bignum1 = new BigDecimal(String.valueOf(total));
        double formatBlance = bignum1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        if(userRechargeDTO.getTurnoverAmount() < 0) {
            return new JSONResult("充值金额不能为负数，充值失败",200);
        }
        else if(userRechargeDTO.getTurnoverAmount() * petrolNums.length > getBalance(user.getUserId())){
            return new JSONResult("充值失败，余额不足",200);
        }
        else if(formatBlance == 0.00){
            return new JSONResult("充值失败，金额不能小于0.01",200);
        }

        if(userRechargeDTO.getTurnoverAmount() < 0) {
            return new JSONResult("充值金额不能为负数，充值失败",200);
        }

        for (int i = 0; i < petrolNums.length; i++) {
            if (petrolNums[i] == null || regionals[i] == null) {
                return new JSONResult("充值户号和地址不一致",200);
            }
        }

        for (int i = 0; i < regionals.length; i++) {
            if (petrolNums[i] == null || regionals[i] == null) {
                return new JSONResult("充值户号和地址不一致",200);
            }
        }

        int result = 0;

        ElectricityRechargeDto temp = new ElectricityRechargeDto();
        temp.setBalance(getBalance(user.getUserId()) - userRechargeDTO.getTurnoverAmount() * petrolNums.length);
        temp.setUserId(user.getUserId());

        int changeBalance = electricityConsumptionMapperExtra.changeBalance(temp);

        for (int i = 0 ; i < petrolNums.length; i++) {
            ElectricityRechargeDto electricityRechargeDto = new ElectricityRechargeDto();
            String orderId = System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15).replace("-", "");
            electricityRechargeDto.setOrderId(orderId);
            electricityRechargeDto.setUserId(user.getUserId());
            electricityRechargeDto.setRegional(regionals[i]);
            electricityRechargeDto.setRechargeAccount(petrolNums[i]);
            electricityRechargeDto.setRechargeAmount(userRechargeDTO.getTurnoverAmount());
            result = electricityConsumptionMapperExtra.insertConsumption(electricityRechargeDto);
        }
        return new JSONResult("插入成功",200,"1");
    }

    @Override
    public JSONResult getCustomers(User user, ElectricityRechargeDto electricityRechargeDto) {
        System.out.println(user.getUserId());
        electricityRechargeDto.setUserId(user.getUserId());
        List<ElectricityRechargeDto> list = electricityConsumptionMapperExtra.getCustomers(electricityRechargeDto);
        return new JSONResult("查询成功",200,list);
    }
}
