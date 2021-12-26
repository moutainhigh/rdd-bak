package com.cqut.czb.bn.service.impl.directCustomerManagerImpl;

import com.cqut.czb.bn.dao.mapper.directCustomerManager.DirectAgentMapperExtra;
import com.cqut.czb.bn.entity.dto.OfflineRecharge.UserRecharge;
import com.cqut.czb.bn.entity.dto.autoRecharge.UserRechargeDTO;
import com.cqut.czb.bn.entity.dto.directCustomers.DirectCustomerManagerDto;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.directCustomerManager.DirectAgentService;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class directAgentImpl implements DirectAgentService {

    @Autowired
    DirectAgentMapperExtra directAgentMapperExtra;

    @Override
    public double getBalance(String userId) {
        DirectCustomerManagerDto directCustomerManagerDto = directAgentMapperExtra.getBalance(userId);
        if (directCustomerManagerDto == null) {
            directCustomerManagerDto = new DirectCustomerManagerDto();
            directCustomerManagerDto.setBalance(0);
        }
            return directCustomerManagerDto.getBalance();

    }

    @Override
    public JSONResult insertRecharge(User user, UserRechargeDTO userRechargeDTO) {
        String[] petrolNums;
        if (userRechargeDTO.getPetrolNums() != null && userRechargeDTO.getPetrolNums() != " "){
            petrolNums = userRechargeDTO.getPetrolNums().split(",");
        }else {
            return new JSONResult("充值失败",200);
        }

        double total = userRechargeDTO.getTurnoverAmount() * petrolNums.length;
        double oldBalance = directAgentMapperExtra.findBalanceById(user.getUserId());
        double oldConsumption = directAgentMapperExtra.findConsumptionById(user.getUserId());
        System.out.println(total);
        DirectCustomerManagerDto directCustomerManagerDto = new DirectCustomerManagerDto();
        directCustomerManagerDto.setBalance(oldBalance - total);
        directCustomerManagerDto.setConsumptionAmount(total + oldConsumption);
        if (oldBalance - total < 0) {
            return new JSONResult("余额不足",200);
        }
        directCustomerManagerDto.setUserId(user.getUserId());
        directAgentMapperExtra.changeBalance(directCustomerManagerDto);
        directAgentMapperExtra.changeConsumption(directCustomerManagerDto);
        for (int i = 0; i < petrolNums.length; i++) {
//            String account = directAgentMapperExtra.findAccountById(petrolNums[i]);
//            System.out.println(petrolNums[i]);
            DirectCustomerManagerDto directCustomerManagerDto1 = new DirectCustomerManagerDto();
            directCustomerManagerDto1.setUserId(user.getUserId());
            directCustomerManagerDto1.setOrderId(System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15).replace("-", ""));
            directCustomerManagerDto1.setRechargeAmount(userRechargeDTO.getTurnoverAmount());
            directCustomerManagerDto1.setRechargeAccount(petrolNums[i]);
            directAgentMapperExtra.insertRecharge(directCustomerManagerDto1);
        }
        return new JSONResult("充值成功",200,"1");
    }

    @Override
    public JSONResult getRechargeDetails(String userId, DirectCustomerManagerDto pageDTO) {
        System.out.println("userId: " + userId +" ,account: " + pageDTO.getAccount());
        pageDTO.setUserId(userId);
        List<DirectCustomerManagerDto> userRechargeList = directAgentMapperExtra.getRechargeDetails(pageDTO);
        PageInfo<DirectCustomerManagerDto> pageInfo = new PageInfo<>(userRechargeList);
        return new JSONResult("列表数据查询成功", 200, pageInfo);
    }

    @Override
    public JSONResult getTotalRechargeAmount(String userId) {
        System.out.println("充值ID: " + userId);
        double result = directAgentMapperExtra.findTotalRecharge(userId);
        if (result > 0) {
            return new JSONResult("查询成功",200,result);
        }
        return new JSONResult("未找到",200,0);
    }
}
